package com.learning.blog.dto.request

/**
 * Request DTO for creating a new user
 * Used in POST /api/users
 */
data class CreateUserRequest(val name: String,val email: String,val age:Int)
