package com.example.barchasb.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object ApiClient {
    //    private const val BASE_URL = "http://127.0.0.1:8000" // Updated base URL
    private const val BASE_URL = "http://65.109.219.11:8000" // Replace with your backend URL


    private val loggingInterceptor = okhttp3.logging.HttpLoggingInterceptor().apply {
        level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient =
        okhttp3.OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    val retrofit: retrofit2.Retrofit = retrofit2.Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .client(httpClient).build()

    inline fun <reified T> create(): T = retrofit.create(T::class.java)
}
