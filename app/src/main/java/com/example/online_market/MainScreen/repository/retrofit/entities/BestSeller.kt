package com.example.online_market.MainScreen.repository.retrofit.entities

import com.google.gson.annotations.SerializedName

data class BestSeller(
    @SerializedName("discount_price")
    val discountPrice: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_favorites")
    val isFavorites: Boolean,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("price_without_discount")
    val priceWithoutDiscount: Int,
    @SerializedName("title")
    val title: String
)
