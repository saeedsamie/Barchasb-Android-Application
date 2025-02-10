package com.example.barchasb.api

import com.example.barchasb.BuildConfig

object ApiClient {

    private val loggingInterceptor = okhttp3.logging.HttpLoggingInterceptor().apply {
        level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient =
        okhttp3.OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    val retrofit: retrofit2.Retrofit = retrofit2.Retrofit.Builder().baseUrl(BuildConfig.API_URL)
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .client(httpClient).build()

    inline fun <reified T> create(): T = retrofit.create(T::class.java)
}
