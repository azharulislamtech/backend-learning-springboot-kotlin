package com.learning.crudtwo.controller

import com.learning.crudtwo.common.ApiResponse
import com.learning.crudtwo.dto.request.AddEmployeeRequest
import com.learning.crudtwo.dto.request.PatchEmployeeRequest
import com.learning.crudtwo.dto.request.UpdateEmployeeRequest
import com.learning.crudtwo.dto.response.EmployeeResponse
import com.learning.crudtwo.service.EmployeeService
import com.learning.crudtwo.model.Employee
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/employees")
class EmployeeController(private val service: EmployeeService) {

    @GetMapping()
    fun getAllEmployees(): ResponseEntity<ApiResponse<List<EmployeeResponse>>> {
        val employees = service.getAllEmployees().map { EmployeeResponse.fromEmployee(it) }
        return ResponseEntity.ok(
            ApiResponse(success = true, message = "Employees retrieved successfully", data = employees)
        )
    }

    @PostMapping()
    fun registerEmployee(@RequestBody employee: AddEmployeeRequest): ResponseEntity<ApiResponse<EmployeeResponse>> {
        val newEmployee = Employee(name = employee.name, email = employee.email)
        val savedEmployee = service.registerEmployee(newEmployee)
        return ResponseEntity.ok(ApiResponse(success = true, message = "Employee registered successfully", data = EmployeeResponse.fromEmployee(savedEmployee)))
    }

    @GetMapping("/{id}")
    fun findEmployeeById(@PathVariable id: Long): ResponseEntity<ApiResponse<EmployeeResponse>> {
        val employee = service.getEmployeeById(id)
        return if (employee != null) {
            ResponseEntity.ok(ApiResponse(success = true, message = "Employee found successfully", data = EmployeeResponse.fromEmployee(employee)))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse(success = false, message = "Employee with id $id not found", data = null))
        }
    }

    @PutMapping("/{id}")
    fun updateEmployee(@PathVariable id: Long, @RequestBody employee: UpdateEmployeeRequest): ResponseEntity<ApiResponse<EmployeeResponse>> {
        val updatedEmployee = service.updateEmployee(id, employee)
        return if (updatedEmployee != null) {
            ResponseEntity.ok(ApiResponse(success = true, message = "Employee updated successfully", data = EmployeeResponse.fromEmployee(updatedEmployee)))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse(success = false, message = "Employee with id $id not found", data = null))
        }
    }

    @PatchMapping("/{id}")
    fun patchEmployee(@PathVariable id: Long, @RequestBody employee: PatchEmployeeRequest): ResponseEntity<ApiResponse<EmployeeResponse>> {
        val patchedEmployee = service.patchEmployee(id, employee)
        return if (patchedEmployee != null) {
            ResponseEntity.ok(ApiResponse(success = true, message = "Employee patched successfully", data = EmployeeResponse.fromEmployee(patchedEmployee)))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse(success = false, message = "Employee with id $id not found", data = null))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id: Long): ResponseEntity<ApiResponse<Nothing?>> {
        return try {
            val deleted = service.deleteEmployeeById(id)
            if (deleted) {
                ResponseEntity.ok(ApiResponse(success = true, message = "Employee deleted successfully", data = null))
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse(success = false, message = "Employee with id $id not found", data = null))
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse(success = false, message = "Failed to delete employee: ${e.message}", data = null))
        }
    }

    @PutMapping("/{id}")
    fun updateExistingEmployee(@PathVariable id: Long, @RequestBody employee: UpdateEmployeeRequest): ResponseEntity<ApiResponse<EmployeeResponse>>{
        val updatedEmployee=service.updateEmployee(id, employee)
        if (updatedEmployee!=null){
            return ResponseEntity.ok(ApiResponse(success = true, message = "Employee updated successfully",data= EmployeeResponse.fromEmployee(updatedEmployee)))
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse(success = false, message = "Employee with id $id not found", data = null))
        }
    }
}
