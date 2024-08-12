package com.ademozalp.yemek_burada.model

import com.ademozalp.yemek_burada.model.base.Base
import com.ademozalp.yemek_burada.model.enums.OrderStatus
import com.ademozalp.yemek_burada.util.DatabaseConstant
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = DatabaseConstant.ORDER)
data class Order @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: OrderStatus,

    @Column(nullable = false)
    var totalAmount: BigDecimal,

    var note: String? = null,

    @ManyToMany
    @JoinTable(
        name = DatabaseConstant.ORDER_FOOD,
        joinColumns = [JoinColumn(name = DatabaseConstant.ORDER_ID)],
        inverseJoinColumns = [JoinColumn(name = DatabaseConstant.FOOD_ID)]
    )
    var foods: List<Food> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = DatabaseConstant.DESK_ID)
    var desk: Desk? = null

) : Base() {
    constructor() : this(null, OrderStatus.PREPARING, BigDecimal.ZERO, "")
}
