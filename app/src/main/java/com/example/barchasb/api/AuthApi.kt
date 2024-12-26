package com.example.barchasb.api

import retrofit2.http.*
import retrofit2.Response

data class User(val username: String, val password: String)
data class Token(val token: String)

data class LogoutRequest(val token: String)

interface AuthApi {
    @POST("/api/v1/auth/signup")
    suspend fun signup(@Body user: User): Response<Map<String, String>>

    @POST("/api/v1/auth/login")
    suspend fun login(@Body user: User): Response<Token>

    @POST("/api/v1/auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Map<String, String>>
}
