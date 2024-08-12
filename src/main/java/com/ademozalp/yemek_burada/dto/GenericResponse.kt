package com.ademozalp.yemek_burada.dto

data class GenericResponse @JvmOverloads constructor(
    val message: String?=null,
    val code: Int?=200,
    val data: Any? = null
)
