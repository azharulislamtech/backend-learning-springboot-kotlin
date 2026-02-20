package com.learning.blog.controller

import com.learning.blog.common.ApiResponse
import com.learning.blog.dto.request.CreateEmployeeRequest
import com.learning.blog.dto.request.UpdateEmployeeRequest
import com.learning.blog.dto.response.EmployeeResponse
import com.learning.blog.model.Employee
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/employees")
class EmployeeController {

    private val employees = mutableListOf<Employee>()
    private var currentId = 1L

    @GetMapping()
    fun getAllEmployees(): ResponseEntity<ApiResponse<List<EmployeeResponse>>> {
        val employeeResponse = employees.map { EmployeeResponse.fromEmployee(it) }
        return if (employeeResponse.isNotEmpty()) {
            ResponseEntity.ok(ApiResponse(success = true, message = "Fetch employee data", data = employeeResponse))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse(success = false, message = "No data found", data = null))
        }

    }

    @GetMapping("/{id}")
    fun getEmployeeById(@PathVariable id: Long): ResponseEntity<ApiResponse<EmployeeResponse>> {
        val employee = employees.find { it.id == id }
        return if (employee != null) {
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Employee found successfully",
                    data = EmployeeResponse.fromEmployee(employee)
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse(success = false, message = "Employee with id $id not found", data = null))
        }
    }

    @PostMapping
    fun createEmployee(@RequestBody request: CreateEmployeeRequest): ResponseEntity<ApiResponse<EmployeeResponse>> {
        val newEmployee = Employee(
            id = currentId++,
            name = request.name,
            email = request.email,
            password = request.password,
            department = request.department
        )
        employees.add(newEmployee)
        return ResponseEntity.created(URI.create("/api/employees/${newEmployee.id}")).body(
            ApiResponse(
                success = true,
                message = "Employee created successfully",
                data = EmployeeResponse.fromEmployee(newEmployee)
            )
        )
    }

    @PutMapping("/{id}")
    fun updateEmployee(
        @PathVariable id: Long,
        @RequestBody request: UpdateEmployeeRequest
    ): ResponseEntity<ApiResponse<EmployeeResponse>> {
        val index = employees.indexOfFirst { it.id == id }
        if (index == -1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse(success = false, message = "Employee id with $id not found", data = null))
        }
        val employee = employees[index]
        val updatedEmployee = employee.copy(name = request.name, email = request.email, department = request.department)
        employees[index] = updatedEmployee
        return ResponseEntity.ok().body(
            ApiResponse(
                success = true,
                message = "Employee updated successfully",
                data = EmployeeResponse.fromEmployee(updatedEmployee)
            )
        )
    }

    @PatchMapping("/{id}")
    fun patchEmployee(
        @PathVariable id: Long,
        @RequestBody update: Map<String, Any>
    ): ResponseEntity<ApiResponse<EmployeeResponse>> {
        val index = employees.indexOfFirst { it.id == id }

        if (index == -1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse(success = false, message = "Employee id with $id not found"))

        var employee = employees[index]
        update.forEach { (key, value) ->
            when (key) {
                "name" -> employee = employee.copy(name = value.toString())
                "email" -> employee = employee.copy(email = value.toString())
                "department" -> employee = employee.copy(department = value.toString())
            }
        }
        employees[index] = employee
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Employee updated successfully",
                data = EmployeeResponse.fromEmployee(employee)
            )
        )

    }


    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id: Long): ResponseEntity<ApiResponse<String>> {
        val deleted = employees.removeIf { it.id == id }
        if (deleted) {
            return ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Employee  removed successfully",
                    data = "Employee id $id deleted successfully"
                )
            )
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse(success = false, message = "Employee id $id not found", data = null))
    }

    @GetMapping("/search")
    fun searchEmployees(@RequestParam query: String? = null): ResponseEntity<ApiResponse<List<EmployeeResponse>>> {
        val matchEmployees = if (query.isNullOrEmpty()) {
            employees
        } else {
            employees.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.department.contains(query, ignoreCase = true) ||
                        it.email.contains(query, ignoreCase = true)
            }
        }
        val employeeResponse = matchEmployees.map { EmployeeResponse.fromEmployee(it) }
        return ResponseEntity.ok(ApiResponse(success = true, message = "Fetch employee data", data = employeeResponse))
    }

}