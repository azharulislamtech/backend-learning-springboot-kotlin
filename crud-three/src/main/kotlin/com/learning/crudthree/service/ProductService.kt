package com.learning.crudthree.service

import com.learning.crudthree.common.exception.ProductNotFoundException
import com.learning.crudthree.dto.request.AddProductRequest
import com.learning.crudthree.model.Product
import com.learning.crudthree.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(private val productRepository: ProductRepository) {

    private val logger = LoggerFactory.getLogger(ProductService::class.java)

    @Transactional(readOnly = true)
    fun getAllProducts(): List<Product> = productRepository.findAll()

    @Transactional(readOnly = true)
    fun getProductById(id: Long): Product =
        productRepository.findById(id)
            .orElseThrow { ProductNotFoundException(id) }

    @Transactional
    fun createProduct(request: AddProductRequest): Product {
        val sanitizedName = request.name.trim()
        val sanitizedImageUrl = request.imageUrl?.trim()?.takeIf { it.isNotEmpty() }

        logger.info("Creating product with name={}", sanitizedName)

        val product = Product(
            name = sanitizedName,
            price = request.price,
            imageUrl = sanitizedImageUrl
        )

        return productRepository.save(product).also {
            logger.info("Product created successfully with id={}", it.id)
        }
    }
}


