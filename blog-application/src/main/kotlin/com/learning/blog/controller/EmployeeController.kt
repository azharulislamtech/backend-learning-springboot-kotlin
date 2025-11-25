package com.learning.blog.controller

import com.learning.blog.model.APIResponse
import com.learning.blog.model.Employee
import com.learning.blog.model.EmployeeResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/employee")
class EmployeeController {
    val employees= mutableListOf<Employee>()
    val currentId=1L

    @GetMapping()
    fun getAllEmployee(): ResponseEntity<APIResponse<List<EmployeeResponse>>>{
        val employeeResponses=employees.map { employee ->
            EmployeeResponse.fromEmployee(employee)
        }

        return if (employeeResponses.isNotEmpty()){
            ResponseEntity.ok(
                APIResponse(
                    success = true,
                    message="Employee found",
                    data= employeeResponses
                )
            )
        }else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                APIResponse(
                    success = false,
                    message="No employee found",
                    data= null
                )

            )
        }
    }

}