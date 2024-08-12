package com.ademozalp.yemek_burada.dto.food.request

import jakarta.validation.constraints.NotNull

data class SearchFoodRequest @JvmOverloads constructor(
    @field:NotNull(message = "Page alanı boş olamaz") val page: Int?,
    @field:NotNull(message = "Size alanı boş olamaz") val size: Int?,
    val name: String? = null,
    val status: String? = null,
    val sortBy: String? = "id",
    val sortDirection: String? = "ASC"
)
