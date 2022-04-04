package com.example.online_market.CartScreen.repository.retrofit.entities

import com.google.gson.annotations.SerializedName

data class Basket(
    @SerializedName("images")
    val images: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("title")
    val title: String
)