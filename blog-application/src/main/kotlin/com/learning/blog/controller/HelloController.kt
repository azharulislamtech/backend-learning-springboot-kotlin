package com.learning.blog.controller

import org.springframework.web.bind.annotation.*


/**
 * Simple Hello World controller
 * Purpose: Understand basic REST controller concepts before building complex APIs
 */
@RestController
@RequestMapping("/api")
class HelloController {

    /**
     * Simple GET endpoint
     * URL: GET /api/hello
     * Returns: Plain text greeting
     */
    @GetMapping("/hello")
    fun sayHello(): String {
        return "Hello from Blog Application! 🚀"
    }

    /**
     * GET endpoint with path variable
     * URL: GET /api/greet/{name}
     * Example: GET /api/greet/Rakib
     * Returns: Personalized greeting
     */
    @GetMapping("/greet/{name}")
    fun greetUser(@PathVariable name: String): String {
        return "Hello, $name! Welcome to Backend Development with Kotlin & Spring Boot!"
    }

    /**
     * GET endpoint with query parameter
     * URL: GET /api/greet?name=Rakib
     * Example: GET /api/greet?name=Sadia
     * Returns: Greeting with optional name parameter
     */
    @GetMapping("/greet")
    fun greetWithQuery(
        @RequestParam(required = false, defaultValue = "Guest") name: String
    ): String {
        return "Hello, $name! Thanks for trying our API!"
    }

    /**
     * GET endpoint with multiple query parameters
     * URL: GET /api/info?name=Rakib&age=28
     * Demonstrates handling multiple parameters
     */
    @GetMapping("/info")
    fun getUserInfo(
        @RequestParam name: String,
        @RequestParam(required = false) age: Int?
    ): String {
        return if (age != null) {
            "Name: $name, Age: $age years old"
        } else {
            "Name: $name, Age: Not provided"
        }
    }

    /**
     * GET endpoint returning JSON object
     * URL: GET /api/status
     * Demonstrates automatic JSON serialization
     */
    @GetMapping("/status")
    fun getStatus(): Map<String, Any?> {
        return mapOf(
            "status" to "running",
            "version" to "1.0.0",
            "timestamp" to System.currentTimeMillis()
        )
    }
}