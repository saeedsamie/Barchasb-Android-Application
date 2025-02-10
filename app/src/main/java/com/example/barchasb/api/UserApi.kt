package com.example.barchasb.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

data class User(val name: String, val password: String)

data class TokenResponse(
    val access_token: String, val token_type: String
)

data class UpdateUserRequest(val new_name: String?)
data class UpdatePasswordRequest(val new_password: String)

interface UserApi {
    @POST("users/signup")
    suspend fun signup(@Body user: User): Response<Map<String, String>>

    @POST("users/login")
    suspend fun login(@Body user: User): Response<TokenResponse>

    @GET("users/user")
    suspend fun getUserInfo(@Header("Authorization") token: String): Response<UserProfile>

    @PUT("users/user")
    suspend fun updateUser(
        @Header("Authorization") token: String, @Body updateUserRequest: UpdateUserRequest
    ): Response<Map<String, String>>

    @PUT("users/user/password")
    suspend fun updatePassword(
        @Header("Authorization") token: String, @Body updatePasswordRequest: UpdatePasswordRequest
    ): Response<Map<String, String>>
}

data class UserProfile(
    val id: String, val name: String, val points: Int, val label_count: Int
)