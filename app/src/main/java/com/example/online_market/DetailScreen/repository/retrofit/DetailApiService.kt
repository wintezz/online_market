package com.example.online_market.DetailScreen.repository.retrofit

import com.example.online_market.DetailScreen.repository.retrofit.entities.DetailInfoResponse
import com.example.online_market.utils.RetrofitExtensions.Companion.setClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface DetailApiService {

    // методы
    @GET("productdetails")
    suspend fun getDetailInfo(@Header("x-apikey") apiKey: String): Response<DetailInfoResponse>

}