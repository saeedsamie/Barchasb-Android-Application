package com.example.barchasb.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

data class User(val username: String, val password: String)

data class TokenResponse(
    val access_token: String,
    val token_type: String
)

interface AuthApi {
    @POST("/api/v1/auth/signup")
    suspend fun signup(@Body user: User): Response<Map<String, String>>

    @POST("/api/v1/auth/login")
    suspend fun login(@Body user: User): Response<TokenResponse> // Adjusted response type

    @POST("/api/v1/auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Map<String, String>>
}
