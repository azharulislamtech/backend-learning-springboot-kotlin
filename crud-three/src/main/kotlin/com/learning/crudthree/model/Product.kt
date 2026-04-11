package com.learning.crudthree.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "products")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 120)
    var name: String,

    @Column(nullable = false, precision = 12, scale = 2)
    var price: BigDecimal,

    @Column(length = 500)
    var imageUrl: String? = null
)
//why you use error block ? can we manage it by success status and message when it error using ApiResponse Wrapper
////why you use @Transactional(readOnly = true) or @Transactional in service class