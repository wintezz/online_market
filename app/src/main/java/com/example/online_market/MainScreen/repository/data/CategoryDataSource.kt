package com.example.online_market.MainScreen.repository.data

interface CategoryDataSource {
    fun getCategory() : List<CategoryDto>
}