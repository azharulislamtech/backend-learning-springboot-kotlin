package com.learning.blog.common

import java.time.LocalDateTime


/**
 * Generic API Response wrapper
 * Provides consistent response structure across all endpoints
 */
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
