package com.ademozalp.yemek_burada.dto.order.request

data class FilterOrderRequest(
    val page: Int? = 0,
    val size: Int? = 10,
    val startDate: String?,
    val endDate: String?
)
