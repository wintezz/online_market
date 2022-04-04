package com.example.online_market.MainScreen.repository.retrofit

import com.example.online_market.MainScreen.repository.retrofit.entities.MainInfoResponse
import com.example.online_market.utils.RetrofitExtensions.Companion.setClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface MainApiService {

    // методы
    @GET("main")
    suspend fun getMainInfo(@Header("x-apikey") apiKey: String): Response<MainInfoResponse>

    companion object {
        private const val BASE_URL = "https://run.mocky.io/v3/654bd15e-b121-49ba-a588-960956b15175"

        fun create(): MainApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .setClient()
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MainApiService::class.java)
        }

    }

}