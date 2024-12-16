package com.example.barchasb.api

import retrofit2.http.*
import retrofit2.Response

data class Task(val id: Int, val title: String, val description: String, val total_labels: Int = 0)
data class Submission(val task_id: Int, val user: String, val label: String)

interface TasksApi {
    @GET("/tasks")
    suspend fun getTasks(): Response<List<Task>>

    @GET("/tasks/{task_id}")
    suspend fun getTask(@Path("task_id") taskId: Int): Response<Task>

    @POST("/tasks/{task_id}/submit")
    suspend fun submitTask(
        @Path("task_id") taskId: Int,
        @Body submission: Submission
    ): Response<Map<String, String>>

    @POST("/tasks/report")
    suspend fun reportTask(
        @Query("task_id") taskId: Int,
        @Query("reason") reason: String
    ): Response<Map<String, String>>
}

