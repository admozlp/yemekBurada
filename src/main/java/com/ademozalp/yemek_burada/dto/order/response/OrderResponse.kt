package com.ademozalp.yemek_burada.dto.order.response

import com.ademozalp.yemek_burada.dto.desk.response.DeskResponse
import com.ademozalp.yemek_burada.dto.food.response.FoodResponse
import com.ademozalp.yemek_burada.model.Order

data class OrderResponse(
    val id: Long,
    val status: String,
    val totalAmount: String,
    val note: String?,
    val createdDate: String,
    val updatedDate: String? = null,
    val foods: List<FoodResponse>,
    val desk: DeskResponse?
) {
    companion object {
        @JvmStatic
        fun convert(from: Order): OrderResponse {
            return OrderResponse(
                id = from.id!!,
                status = from.status.name,
                totalAmount = from.totalAmount.toString(),
                note = from.note,
                createdDate = from.cratedDateTime.toString(),
                updatedDate = from.updatedDateTime?.toString(),
                foods = from.foods.map { FoodResponse.convert(it) },
                desk = from.desk?.let { DeskResponse.convert(it) }
            )
        }
    }
}
