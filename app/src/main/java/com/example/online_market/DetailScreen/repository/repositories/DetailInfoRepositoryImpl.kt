package com.example.online_market.DetailScreen.repository.repositories

import com.example.online_market.DetailScreen.repository.repositories.interfaces.DetailInfoRepository
import com.example.online_market.DetailScreen.repository.retrofit.DetailApiService
import com.example.online_market.DetailScreen.repository.retrofit.entities.DetailInfoResponse
import retrofit2.Response

class DetailInfoRepositoryImpl(private val detailApiService: DetailApiService) :
    DetailInfoRepository {
    override suspend fun getDetailInfo(apiKey: String): Response<DetailInfoResponse> =
        detailApiService.getDetailInfo(apiKey)
}