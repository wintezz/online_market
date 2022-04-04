package com.example.online_market.DetailScreen.repository.retrofit.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailInfoResponseItem(
    @SerializedName("CPU")
    val cPU: String,
    @SerializedName("camera")
    val camera: String,
    @SerializedName("capacity")
    val capacity: List<String>,
    @SerializedName("color")
    val color: List<String>,
    @SerializedName("_id")
    val _id: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("is_favorites")
    val isFavorites: Boolean,
    @SerializedName("price")
    val price: Int,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("sd")
    val sd: String,
    @SerializedName("ssd")
    val ssd: String,
    @SerializedName("title")
    val title: String
) : Parcelable