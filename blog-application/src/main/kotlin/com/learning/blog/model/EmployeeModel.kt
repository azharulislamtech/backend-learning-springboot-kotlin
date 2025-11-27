package com.learning.blog.model

import java.time.LocalDateTime

data class Employee(val id: Long,val name:String,val email:String,val password:String,val department: String,val createdAt: LocalDateTime= LocalDateTime.now())
data class APIResponse<T>(val success: Boolean,val message:String,val data:T?=null,val timestamp:LocalDateTime= LocalDateTime.now())
data class CreateEmployeeRequest(val name: String,val email: String,val password: String,val department: String)
data class UpdateEmployeeRequest(val name: String,val email:String,val department: String)
data class EmployeeResponse(val id: Long,val name:String,val email:String,val department:String,val createdAt:LocalDateTime){
    companion object{
        fun fromEmployee(employee:Employee)=
            EmployeeResponse(employee.id,employee.name,employee.email,employee.department,employee.createdAt)
    }
}