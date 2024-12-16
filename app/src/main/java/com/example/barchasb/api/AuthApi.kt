package com.example.barchasb.api

import retrofit2.http.*
import retrofit2.Response

data class User(val username: String, val password: String)

interface AuthApi {
    @POST("/auth/signup")
    suspend fun signup(@Body user: User): Response<Map<String, String>>

    @POST("/auth/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<Map<String, String>>

    @POST("/auth/logout")
    suspend fun logout(@Query("username") username: String): Response<Map<String, String>>
}
