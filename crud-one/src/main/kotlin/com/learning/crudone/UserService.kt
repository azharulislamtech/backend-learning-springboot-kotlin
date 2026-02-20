package com.learning.crudone

import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository) {
    fun getAll():List<User>{
        return repository.findAll()
    }
    fun getById(id:Long)= repository.findById(id)

    fun save(user:User):User{
        return repository.save(user)
    }
}