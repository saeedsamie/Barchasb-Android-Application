package com.example.barchasb.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.UUID

data class Task(
    val id: UUID,
    val type: String,
    val data: Map<String, Any>,
    val point: Int,
    val title: String,
    val description: String,
    val tags: List<String>
)

data class Label(
    val task_id: UUID,
    val content: Any
)

data class Report(
    val task_id: UUID,
    val detail: String = "Reported by user"
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
        @Body label: Label
    ): Response<Map<String, String>>

    @POST("tasks/report")
    suspend fun reportTask(
        @Header("Authorization") token: String,
        @Body report: Report
    ): Response<Map<String, String>>
}


