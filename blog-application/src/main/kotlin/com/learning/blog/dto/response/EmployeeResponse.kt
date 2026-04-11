package com.learning.blog.dto.response

import com.learning.blog.model.Employee
import java.time.LocalDateTime

data class EmployeeResponse(
    val id: Long,
    val name: String,
    val email: String,
    val department: String,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromEmployee(employee: Employee) =
            EmployeeResponse(employee.id, employee.name, employee.email, employee.department, employee.createdAt)
    }
}