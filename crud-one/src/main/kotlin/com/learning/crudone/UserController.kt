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
    // ১. সব ইউজার পাওয়ার জন্য (GET)
    @GetMapping
    fun getAllUsers()=service.getAll()


    // ২. নির্দিষ্ট একজন ইউজার পাওয়ার জন্য (GET with ID)
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {

        val user=service.getById(id)
        return if(user!=null)
            ResponseEntity.ok(user)
        else
            ResponseEntity.notFound().build()
    }

    // ৩. নতুন ইউজার তৈরির জন্য (POST)
    @PostMapping
    fun saveUser(@RequestBody user:User)=service.save(user)

    // ৪. ইউজার আপডেট করার জন্য (PUT)
    @PutMapping("/{id}")
    fun updateUserById(@PathVariable id:Long,@RequestBody updatedUser:User): ResponseEntity<User>
    {
        val updatedUser=service.updateUser(id,updatedUser)
       return if(updatedUser!=null)
           ResponseEntity.ok(updatedUser)
        else
              ResponseEntity.notFound().build()
    }

    // ৫. ইউজার ডিলিট করার জন্য (DELETE)
    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id:Long): ResponseEntity<Void>{

        return if(service.deleteById(id))
            ResponseEntity.noContent().build()
        else
            ResponseEntity.notFound().build()
    }


}