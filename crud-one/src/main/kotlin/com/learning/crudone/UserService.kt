package com.learning.crudone

import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository) {

    // ২. Read All (সব ইউজারের লিস্ট দেখা)
    fun getAll():List<User>{
        return repository.findAll()
    }
    // ৩. Read by ID (নির্দিষ্ট একজনকে খুঁজে বের করা)
    fun getById(id:Long):User?= repository.findById(id).orElse(null)

    // ১. Save (নতুন ইউজার সেভ করা)
    fun save(user:User):User{
        return repository.save(user)
    }
    // ৫. Delete (ইউজার মুছে ফেলা)
    fun deleteById(id:Long): Boolean=
        if(repository.existsById(id)){
            repository.deleteById(id)
             true
        }else {
            false
        }

    // ৪. Update (তথ্য পরিবর্তন করা)
    fun updateUser(id: Long, updatedUser: User): User? {
        return if (repository.existsById(id)) {
            // আইডি সেট করে save করলে সেটি নতুন করে না বানিয়ে আপডেট করে
            val userToSave = updatedUser.copy(id = id)
            repository.save(userToSave)
        } else {
            null
        }
    }
}