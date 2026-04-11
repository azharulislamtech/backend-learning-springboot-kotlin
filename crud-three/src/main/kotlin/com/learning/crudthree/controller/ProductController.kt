package com.learning.crudthree.controller

import com.learning.crudthree.common.ApiResponse
import com.learning.crudthree.dto.request.AddProductRequest
import com.learning.crudthree.dto.response.ProductResponse
import com.learning.crudthree.mapper.toResponse
import com.learning.crudthree.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/v1/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getAllProducts(): ResponseEntity<ApiResponse<List<ProductResponse>>> {
        val productResponses = productService.getAllProducts().map { it.toResponse() }
        val response = ApiResponse(
            success = true,
            message = "Products retrieved successfully",
            data = productResponses
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<ApiResponse<ProductResponse>> {
        val productResponse = productService.getProductById(id).toResponse()
        val response = ApiResponse(
            success = true,
            message = "Product retrieved successfully",
            data = productResponse
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun createProduct(@Valid @RequestBody request: AddProductRequest): ResponseEntity<ApiResponse<ProductResponse>> {
        val savedProduct = productService.createProduct(request)
        val productResponse = savedProduct.toResponse()
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(productResponse.id)
            .toUri()

        val response = ApiResponse(
            success = true,
            message = "Product created successfully",
            data = productResponse
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .location(location)
            .body(response)
    }
}
