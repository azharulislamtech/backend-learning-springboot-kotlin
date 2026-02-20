package com.learning.blog.model


import java.time.LocalDateTime

/**
 * User data model
 * Represents a user in the system
 */
data class User(
    val id: Long,
    val name: String,
    val email: String,
    val age: Int,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

