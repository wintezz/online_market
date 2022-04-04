package com.example.online_market.CartScreen.repository.retrofit

import com.example.online_market.CartScreen.repository.retrofit.entities.CartResponse
import com.example.online_market.utils.RetrofitExtensions.Companion.setClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface CartApiService {

    // методы
    @GET("mycart")
    suspend fun getCart(@Header("x-apikey") apiKey: String): Response<CartResponse>


}