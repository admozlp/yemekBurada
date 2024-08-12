package com.ademozalp.yemek_burada.dto.food.request

import com.ademozalp.yemek_burada.model.Food
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal

data class CreateFoodRequest constructor(
    @field:Size(min = 2, max = 200, message = "Yemek ismi 2 ile 200 karakter arasıda olmalıdır") val name: String?,
    val description: String?,
    @field:NotEmpty(message = "Yemek fiyatı boş olamaz") val price: String?,
    val readinessTime: Int?,
    val photo: MultipartFile?
) {
    companion object {
        @JvmStatic
        fun convert(from: CreateFoodRequest): Food {
            return Food(
                from.name.orEmpty(),
                from.description.orEmpty(),
                BigDecimal(from.price),
                from.readinessTime ?: 0
            )
        }
    }
}
