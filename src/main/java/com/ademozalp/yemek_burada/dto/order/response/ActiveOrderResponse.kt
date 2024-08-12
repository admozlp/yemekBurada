package com.ademozalp.yemek_burada.dto.order.response

import com.ademozalp.yemek_burada.model.Order

data class ActiveOrderResponse(
    val id: Long,
    val status: String,
    val totalAmount: String,
    val cratedDateTime: String? = null,
    val note: String?,
) {
    companion object {
        @JvmStatic
        fun convert(from: Order): ActiveOrderResponse {
            return ActiveOrderResponse(
                id = from.id!!,
                status = from.status.toString(),
                cratedDateTime = from.cratedDateTime.toString(),
                totalAmount = from.totalAmount.toString(),
                note = from.note
            )
        }
    }
}
