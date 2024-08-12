package com.ademozalp.yemek_burada.dto.food.response

import com.ademozalp.yemek_burada.model.Food

data class FoodResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val price: String,
    val photoUrl: String?,
    val readinessTime: Int?,
    val status: String
) {
    companion object {
        @JvmStatic
        fun convert(from: Food): FoodResponse {
            return FoodResponse(
                from.id ?: 0,
                from.name,
                from.description,
                from.price.toString(),
                from.photoUrl,
                from.readinessTime,
                from.status.toString()
            )
        }
    }
}
