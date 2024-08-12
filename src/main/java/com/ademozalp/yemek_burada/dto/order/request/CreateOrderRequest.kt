package com.ademozalp.yemek_burada.dto.order.request

import com.ademozalp.yemek_burada.model.Desk
import com.ademozalp.yemek_burada.model.Food
import com.ademozalp.yemek_burada.model.Order
import com.ademozalp.yemek_burada.model.enums.OrderStatus
import jakarta.validation.constraints.NotNull

data class CreateOrderRequest(
    @field:NotNull(message = "Masa alanı boş olamaz") val deskId: Long,
    val note: String?,
    @field:NotNull(message = "Yemek listesi boş olamaz") val foodIds: List<Long>
) {
    companion object {
        @JvmStatic
        fun convert(from: CreateOrderRequest, foods: List<Food>, desk: Desk): Order {
            return Order(
                desk = desk,
                note = from.note,
                totalAmount = foods.stream().map { it.price }.reduce { acc, price -> acc.add(price) }.get(),
                status = OrderStatus.PREPARING,
                foods = foods
            )
        }
    }
}
