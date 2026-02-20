package com.learning.crudone

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {

        val user=service.getById(id)
        return if(user!=null)
            ResponseEntity.ok(user)
        else
            ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id:Long)=service.deleteById(id)

    @PutMapping("/{id}")
    fun updateUserById(@PathVariable id:Long,@RequestBody updatedUser:User)=service.updateUser(id,updatedUser)


}