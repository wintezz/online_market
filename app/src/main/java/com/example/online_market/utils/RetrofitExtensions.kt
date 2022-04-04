package com.example.online_market.utils

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class RetrofitExtensions {

    companion object {
        fun Retrofit.Builder.setClient() = apply {
            val okHttpClient = OkHttpClient()
                .newBuilder()
                .build()

            this.client(okHttpClient)
        }

        @Suppress("EXPERIMENTAL_API_USAGE")
        fun Retrofit.Builder.addJsonConverter() = apply {
            val json = Json { ignoreUnknownKeys = true }
            val contentType = MediaType.get("application/json")
            this.addConverterFactory(json.asConverterFactory(contentType))
        }

    }
}