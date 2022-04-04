package com.example.online_market.MainScreen.repository.data

import com.example.online_market.R

class CategoryDataSourceImpl() : CategoryDataSource {
    override fun getCategory(): List<CategoryDto> = listOf(
        CategoryDto(
            icon = R.drawable.ic_smartphone,
            name = "Phones"
        ),
        CategoryDto(
            icon = R.drawable.ic_computer,
            name = "Computer"
        ),
        CategoryDto(
            icon = R.drawable.ic_health,
            name = "Health"
        ),
        CategoryDto(
            icon = R.drawable.ic_books,
            name = "Books"
        )
    )
}