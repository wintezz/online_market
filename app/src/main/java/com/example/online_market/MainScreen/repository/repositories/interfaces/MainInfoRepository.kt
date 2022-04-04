package com.example.online_market.MainScreen.repository.repositories.interfaces

import com.example.online_market.MainScreen.repository.retrofit.entities.MainInfoResponse
import retrofit2.Response

interface MainInfoRepository {
    suspend fun getMainInfo(apiKey: String): Response<MainInfoResponse>
}