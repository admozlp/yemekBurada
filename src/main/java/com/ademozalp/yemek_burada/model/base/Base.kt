package com.ademozalp.yemek_burada.model.base

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
open class Base(
    @CreatedDate
    @Column(nullable = false, updatable = false)
    open var cratedDateTime: LocalDateTime? = null,

    @LastModifiedDate
    @Column(insertable = false)
    open var updatedDateTime: LocalDateTime? = null
) {
    constructor() : this(null, null) {}
}