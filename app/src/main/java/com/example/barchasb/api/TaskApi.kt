package com.example.barchasb.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

data class Task(
    val id: Int,
    val type: Int,
    val data: Map<String, Any>,
    val title: String,
    val description: String,
    val point: Int,
    val tags: String
)

data class Submission(
    val id: Int, val user_id: Int, val task_id: Int, val content: Map<String, Any>
)

data class Report(val task_id: Int)

interface TaskApi {
    @GET("/api/v1/tasks/feed")
    suspend fun getTaskFeed(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int
    ): Response<List<Task>>

    @POST("/api/v1/tasks/submit")
    suspend fun submitTask(
        @Header("Authorization") token: String,
        @Body submission: Submission
    ): Response<Map<String, String>>

    @POST("/api/v1/tasks/report")
    suspend fun reportTask(
        @Header("Authorization") token: String,
        @Body report: Report
    ): Response<Map<String, String>>
}


