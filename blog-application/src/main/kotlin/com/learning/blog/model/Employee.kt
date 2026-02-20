package com.learning.blog.model

import java.time.LocalDateTime

data class Employee(
    val id: Long,
    val name: String,
    val email: String,
    val password: String,
    val department: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)


