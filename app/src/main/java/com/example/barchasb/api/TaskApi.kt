package com.example.barchasb.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

data class Task(
    val status: String,
    val task_id: Int,
    val type: String,
    val data: Map<String, Any>,
    val point: Int,
    val title: String,
    val description: String,
    val tags: List<String>
)

data class Submission(
    val task_id: Int,
    val user_id: Int,
    val content: Any
)

data class Report(
    val task_id: Int,
    val user_id: Int,
    val detail: String = "Reported by user"
)

data class TaskResponse(
    val status: String,
    val task_id: Int
)

interface TaskApi {
    @GET("tasks/feed")
    suspend fun getTaskFeed(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int
    ): Response<List<Task>>

    @POST("tasks/submit")
    suspend fun submitTask(
        @Header("Authorization") token: String,
        @Body submission: Submission
    ): Response<Map<String, String>>

    @POST("tasks/report")
    suspend fun reportTask(
        @Header("Authorization") token: String,
        @Body report: Report
    ): Response<Map<String, String>>
}


