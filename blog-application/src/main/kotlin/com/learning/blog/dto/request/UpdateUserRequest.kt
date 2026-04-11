package com.learning.blog.dto.request

/**
 * Request DTO for updating an existing user
 * Used in PUT /api/users/{id}
 */
data class UpdateUserRequest(val name:String,val email:String,val age:Int)
