package com.example.online_market.CartScreen.repository.repositories.interfaces

import CartScreen.domain.retrofit.entities.CartResponse
import retrofit2.Response

interface CartRepository {
    suspend fun getCart(apiKey: String): Response<CartResponse>
}