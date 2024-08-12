package com.ademozalp.yemek_burada.dto.desk.request

import com.ademozalp.yemek_burada.model.Desk
import jakarta.validation.constraints.NotNull


data class CreateDeskRequest @JvmOverloads constructor(
    @field:NotNull(message = "Masa numarası boş olamaz") val no: Int?,
    @field:NotNull(message = "Kapasite boş olamaz") val capacity: Int?
) {
    companion object {
        @JvmStatic
        fun convert(from: CreateDeskRequest): Desk {
            return Desk(
                from.no ?: 0,
                from.capacity ?: 0
            )
        }
    }
}
