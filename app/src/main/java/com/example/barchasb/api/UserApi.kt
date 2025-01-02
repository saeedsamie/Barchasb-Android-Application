package com.example.barchasb.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {
    @GET("/api/v1/auth/me")
    suspend fun getUserProfile(@Header("Authorization") token: String): Response<UserProfile>
}

data class UserProfile(
//    val id: String,
    val username: String,
    val points: Int,
)