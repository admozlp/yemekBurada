package com.ademozalp.yemek_burada.dto.desk.response

import com.ademozalp.yemek_burada.model.Desk

data class DeskResponse(
    val id: Long,
    val no: Int,
    val capacity: Int,
    val status: String
){
    companion object {
        @JvmStatic
        fun convert(from: Desk): DeskResponse {
            return DeskResponse(
                from.id!!,
                from.no,
                from.capacity,
                from.status.toString()
            )
        }
    }
}
