package com.learning.crudthree.dto.request

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class AddProductRequest(
    @field:NotBlank(message = "Product name is required")
    @field:Size(max = 120, message = "Product name must be at most 120 characters")
    val name: String,

    @field:DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @field:Digits(integer = 10, fraction = 2, message = "Price must have at most 10 integer digits and 2 decimal places")
    val price: BigDecimal,

    @field:Size(max = 500, message = "Image URL must be at most 500 characters")
    val imageUrl: String? = null
)
