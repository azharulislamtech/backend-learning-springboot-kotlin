package com.learning.crudthree.dto.response

import java.math.BigDecimal

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: BigDecimal,
    val imageUrl: String?
)
