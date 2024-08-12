package com.ademozalp.yemek_burada.model

import com.ademozalp.yemek_burada.model.base.Base
import com.ademozalp.yemek_burada.model.enums.DeskStatus
import com.ademozalp.yemek_burada.util.DatabaseConstant
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = DatabaseConstant.DESK)
data class Desk @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    var no: Int,

    @Column(nullable = false)
    var capacity: Int,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: DeskStatus,

    @OneToMany(mappedBy = "desk")
    var orders: List<Order> = mutableListOf()

    ) : Base() {
    constructor() : this(null, 0, 0, DeskStatus.AVAILABLE)
    constructor(no: Int, capacity: Int) : this(null, no, capacity, DeskStatus.AVAILABLE)
}
