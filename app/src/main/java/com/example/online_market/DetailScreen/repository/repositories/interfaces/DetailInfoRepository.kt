package com.example.online_market.DetailScreen.repository.repositories.interfaces

import com.example.online_market.DetailScreen.repository.retrofit.entities.DetailInfoResponse
import retrofit2.Response

interface DetailInfoRepository {
    suspend fun getDetailInfo(apiKey: String): Response<DetailInfoResponse>
}