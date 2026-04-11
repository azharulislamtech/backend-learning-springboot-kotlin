package com.learning.crudtwo.dto.response

import com.learning.crudtwo.model.Employee

data class EmployeeResponse(val id: Long, val name: String, val email: String) {
    companion object {
        fun fromEmployee(employee: Employee): EmployeeResponse = EmployeeResponse(
            id = employee.id,
            name = employee.name,
            email = employee.email
        )
    }
}
