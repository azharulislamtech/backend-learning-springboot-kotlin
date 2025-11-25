package com.learning.blog.model

import java.time.LocalDateTime

data class Employee(
    val id : Long,
    val name:String,
    val password:String,
    val email:String,
    val departmentName:String,
    val createdAt: LocalDateTime= LocalDateTime.now(),
)
data class APIResponse<T>(
    val success: Boolean,
    val message: String,
    val data:T?=null,
    val timestamp: LocalDateTime= LocalDateTime.now()
)

data class EmployeeResponse(
    val id:Long,
    val name:String,
    val email: String,
    val departmentName:String,
    val createdAt: LocalDateTime, ){
    companion object{
        fun fromEmployee(employee:Employee)=
            EmployeeResponse(
                id=employee.id,
                name=employee.name,
                email=employee.email,
                departmentName = employee.departmentName,createdAt = employee.createdAt
            )}
}

data class CreateEmployeeRequest(
    val name:String,
    val email: String,
    val password: String,
    val departmentName: String
)
data class UpdateEmployeeRequest(
    val name:String,
    val email: String,
    val departmentName: String
)