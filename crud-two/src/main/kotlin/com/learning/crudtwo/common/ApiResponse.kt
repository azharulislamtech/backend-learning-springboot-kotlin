package com.learning.crudtwo.common

import java.time.LocalDateTime

data class ApiResponse<T> (
    val success: Boolean,
    val message: String,
    val data:T? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
)