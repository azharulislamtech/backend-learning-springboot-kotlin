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

/**
 * Request DTO for creating a new user
 * Used in POST /api/users
 */
data class CreateUserRequest(
    val name: String,
    val email: String,
    val age: Int
)

/**
 * Request DTO for updating an existing user
 * Used in PUT /api/users/{id}
 */
data class UpdateUserRequest(
    val name: String,
    val email: String,
    val age: Int
)

/**
 * Response DTO for user information
 * Used to return user data in responses
 */
data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val age: Int,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromUser(user: User): UserResponse {
            return UserResponse(
                id = user.id,
                name = user.name,
                email = user.email,
                age = user.age,
                createdAt = user.createdAt
            )
        }
    }
}