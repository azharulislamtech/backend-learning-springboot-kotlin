package com.learning.blog.controller

import com.learning.blog.model.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

/**
 * User Management REST API Controller
 * Handles all CRUD operations for users
 *
 * Base URL: /api/users
 * Storage: In-memory (will be replaced with database in Phase 2)
 */
@RestController
@RequestMapping("/api/users")
class UserController {

    // In-memory storage - temporary solution
    // Phase 2 will replace this with database
    private val users = mutableListOf<User>()
    private var currentId = 1L

    /**
     * GET /api/users
     * Retrieve all users
     *
     * @return List of all users wrapped in ApiResponse
     */
    @GetMapping
    fun getAllUsers(): ResponseEntity<ApiResponse<List<UserResponse>>> {
        val userResponses = users.map { UserResponse.fromUser(it) }

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Users retrieved successfully",
                data = userResponses
            )
        )
    }

    /**
     * GET /api/users/{id}
     * Retrieve a single user by ID
     *
     * @param id User ID from URL path
     * @return User data if found, 404 if not found
     */
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<ApiResponse<UserResponse>> {
        val user = users.find { it.id == id }

        return if (user != null) {
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "User found",
                    data = UserResponse.fromUser(user)
                )
            )
        } else {
            ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                    ApiResponse(
                        success = false,
                        message = "User with id $id not found",
                        data = null
                    )
                )
        }
    }

    /**
     * POST /api/users
     * Create a new user
     *
     * @param request CreateUserRequest containing user data
     * @return Created user with 201 status and Location header
     */
    @PostMapping
    fun createUser(
        @RequestBody request: CreateUserRequest
    ): ResponseEntity<ApiResponse<UserResponse>> {
        // Create new user
        val newUser = User(
            id = currentId++,
            name = request.name,
            email = request.email,
            age = request.age
        )

        // Add to storage
        users.add(newUser)

        // Return 201 Created with Location header
        return ResponseEntity
            .created(URI.create("/api/users/${newUser.id}"))
            .body(
                ApiResponse(
                    success = true,
                    message = "User created successfully",
                    data = UserResponse.fromUser(newUser)
                )
            )
    }

    /**
     * PUT /api/users/{id}
     * Update an existing user (complete replacement)
     *
     * @param id User ID to update
     * @param request UpdateUserRequest with new data
     * @return Updated user data or 404 if not found
     */
    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody request: UpdateUserRequest
    ): ResponseEntity<ApiResponse<UserResponse>> {
        val index = users.indexOfFirst { it.id == id }

        return if (index != -1) {
            // Update user (complete replacement)
            val updatedUser = users[index].copy(
                name = request.name,
                email = request.email,
                age = request.age
            )
            users[index] = updatedUser

            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "User updated successfully",
                    data = UserResponse.fromUser(updatedUser)
                )
            )
        } else {
            ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                    ApiResponse(
                        success = false,
                        message = "User with id $id not found",
                        data = null
                    )
                )
        }
    }

    /**
     * PATCH /api/users/{id}
     * Partially update a user
     *
     * @param id User ID to update
     * @param updates Map of fields to update
     * @return Updated user data or 404 if not found
     */
    @PatchMapping("/{id}")
    fun patchUser(
        @PathVariable id: Long,
        @RequestBody updates: Map<String, Any>
    ): ResponseEntity<ApiResponse<UserResponse>> {
        val index = users.indexOfFirst { it.id == id }

        return if (index != -1) {
            var user = users[index]

            // Apply partial updates
            updates["name"]?.let { user = user.copy(name = it as String) }
            updates["email"]?.let { user = user.copy(email = it as String) }
            updates["age"]?.let {
                user = user.copy(age = (it as Number).toInt())
            }

            users[index] = user

            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "User partially updated",
                    data = UserResponse.fromUser(user)
                )
            )
        } else {
            ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                    ApiResponse(
                        success = false,
                        message = "User with id $id not found",
                        data = null
                    )
                )
        }
    }

    /**
     * DELETE /api/users/{id}
     * Delete a user
     *
     * @param id User ID to delete
     * @return Success message or 404 if not found
     */
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<ApiResponse<String>> {
        val removed = users.removeIf { it.id == id }

        return if (removed) {
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "User deleted successfully",
                    data = "User with id $id has been removed"
                )
            )
        } else {
            ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                    ApiResponse(
                        success = false,
                        message = "User with id $id not found",
                        data = null
                    )
                )
        }
    }

    /**
     * GET /api/users/search?name={name}
     * Search users by name (case-insensitive partial match)
     *
     * @param name Name to search for
     * @return List of matching users
     */
    @GetMapping("/search")
    fun searchUsersByName(
        @RequestParam name: String
    ): ResponseEntity<ApiResponse<List<UserResponse>>> {
        val results = users
            .filter { it.name.contains(name, ignoreCase = true) }
            .map { UserResponse.fromUser(it) }

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Search completed. Found ${results.size} user(s)",
                data = results
            )
        )
    }

    /**
     * GET /api/users/filter?minAge={min}&maxAge={max}
     * Filter users by age range
     *
     * @param minAge Minimum age (inclusive)
     * @param maxAge Maximum age (inclusive)
     * @return List of users within age range
     */
    @GetMapping("/filter")
    fun filterUsersByAge(
        @RequestParam minAge: Int,
        @RequestParam maxAge: Int
    ): ResponseEntity<ApiResponse<List<UserResponse>>> {
        val results = users
            .filter { it.age in minAge..maxAge }
            .map { UserResponse.fromUser(it) }

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Found ${results.size} user(s) between age $minAge and $maxAge",
                data = results
            )
        )
    }

    /**
     * GET /api/users/count
     * Get total number of users
     *
     * @return Total user count
     */
    @GetMapping("/count")
    fun getUserCount(): ResponseEntity<ApiResponse<Int>> {
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Total users count",
                data = users.size
            )
        )
    }

    /**
     * GET /api/users/stats
     * Get user statistics
     *
     * @return Statistics including total, average age, oldest, youngest
     */
    @GetMapping("/stats")
    fun getUserStats(): ResponseEntity<ApiResponse<Map<String, Any?>>> {
        if (users.isEmpty()) {
            return ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "No users in system",
                    data = mapOf(
                        "total" to 0,
                        "message" to "No users to calculate statistics"
                    )
                )
            )
        }

        val stats = mapOf(
            "total" to users.size,
            "averageAge" to users.map { it.age }.average(),
            "oldest" to users.maxByOrNull { it.age }?.let {
                mapOf("name" to it.name, "age" to it.age)
            },
            "youngest" to users.minByOrNull { it.age }?.let {
                mapOf("name" to it.name, "age" to it.age)
            }
        )

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "User statistics",
                data = stats
            )
        )
    }
}