package com.example.online_market.CartScreen.repository.repositories.interfaces

import com.example.online_market.CartScreen.repository.retrofit.entities.CartResponse
import retrofit2.Response

interface CartRepository {
    suspend fun getCart(apiKey: String): Response<CartResponse>
}