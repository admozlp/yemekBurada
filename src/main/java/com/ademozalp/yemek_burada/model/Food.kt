package com.ademozalp.yemek_burada.model

import com.ademozalp.yemek_burada.model.base.Base
import com.ademozalp.yemek_burada.model.enums.FoodStatus
import com.ademozalp.yemek_burada.util.DatabaseConstant
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal


@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = DatabaseConstant.FOOD)
data class Food @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var name: String,

    var description: String? = null,

    @Column(nullable = false)
    var price: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: FoodStatus,

    var readinessTime: Int? = null,

    var photoUrl: String? = null,

    @ManyToMany(mappedBy = "foods")
    var orders: List<Order> = mutableListOf()
) : Base() {
    constructor() : this(null, "", "", BigDecimal.ZERO, FoodStatus.AVAILABLE, 0)

    constructor(name: String, description: String?, price: BigDecimal, readinessTime: Int?) :
            this(null, name, description, price, FoodStatus.AVAILABLE, readinessTime)
}
