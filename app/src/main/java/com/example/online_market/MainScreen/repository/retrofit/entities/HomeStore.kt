package com.example.online_market.MainScreen.repository.retrofit.entities

import com.google.gson.annotations.SerializedName

data class HomeStore(
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_new")
    val isNew: Boolean? = null,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("title")
    val title: String
) {
}