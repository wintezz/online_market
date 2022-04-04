package com.example.online_market.CartScreen.repository.repositories

import com.example.online_market.CartScreen.repository.repositories.interfaces.CartRepository
import com.example.online_market.CartScreen.repository.retrofit.entities.CartResponse
import com.example.online_market.CartScreen.repository.retrofit.CartApiService
import retrofit2.Response

class CartRepositoryImpl(private val cartApiService: CartApiService) : CartRepository {

    override suspend fun getCart(apiKey: String): Response<CartResponse> =
        cartApiService.getCart(apiKey)

}