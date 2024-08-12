package com.ademozalp.yemek_burada.dto.food.response

data class FoodSearchResponse(
    val totalPages: Int,
    val currentPage: Int,
    val totalElements: Long,
    val foods: List<FoodResponse>
)
