package com.learning.blog.controller

import com.learning.blog.model.APIResponse
import com.learning.blog.model.Employee
import com.learning.blog.model.EmployeeResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/employees")
class EmployeeController {

    private val employees=mutableListOf<Employee>()
    private var currentId=1L

    @GetMapping()
    fun getAllEmployees(): ResponseEntity<APIResponse<List<EmployeeResponse>>>{
        val employeeResponse=employees.map{ EmployeeResponse.fromEmployee(it) }
        return if(employeeResponse.isNotEmpty()){
            ResponseEntity.ok(APIResponse(success = true, message = "Fetch employee data",data=employeeResponse))
        }else{
             ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIResponse(success = false, message = "No data found",data = null))
        }

    }
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id:Long):ResponseEntity<APIResponse<EmployeeResponse>>{
        val employee=employees.find{it.id==id}
        return if(employee!=null){
            ResponseEntity.ok(APIResponse(success = true, message = "Employee found successfully",data= EmployeeResponse.fromEmployee(employee)))
        }else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIResponse(success = false, message = "Employee with id $id not found", data = null))
        }
    }

}