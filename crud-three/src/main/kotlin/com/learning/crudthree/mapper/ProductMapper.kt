package com.learning.crudthree.mapper

import com.learning.crudthree.dto.response.ProductResponse
import com.learning.crudthree.model.Product

fun Product.toResponse(): ProductResponse {
    val productId = requireNotNull(id) { "Product id must not be null when converting to response" }

    return ProductResponse(
        id = productId,
        name = name,
        price = price,
        imageUrl = imageUrl
    )
}
