package com.example.online_market.MainScreen.repository.repositories

import com.example.online_market.MainScreen.repository.repositories.interfaces.MainInfoRepository
import com.example.online_market.MainScreen.repository.retrofit.MainApiService
import com.example.online_market.MainScreen.repository.retrofit.entities.MainInfoResponse
import retrofit2.Response

class MainInfoRepositoryImpl(private val mainApiService: MainApiService) : MainInfoRepository {
    override suspend fun getMainInfo(apiKey: String): Response<MainInfoResponse> =
        mainApiService.getMainInfo(apiKey)
}