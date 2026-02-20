package com.learning.crudone

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/users")
class UserController(private val service:UserService) {

    @GetMapping
    fun getAllUsers()=service.getAll()

    @PostMapping
    fun saveUser(@RequestBody user:User)=service.save(user)

}