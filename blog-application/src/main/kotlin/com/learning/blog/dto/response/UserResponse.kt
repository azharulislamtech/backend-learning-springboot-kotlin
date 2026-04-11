package com.learning.blog.dto.response

import com.learning.blog.model.User
import java.time.LocalDateTime


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