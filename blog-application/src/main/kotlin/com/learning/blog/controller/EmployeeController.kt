package com.learning.blog.controller

import com.learning.blog.model.APIResponse
import com.learning.blog.model.CreateEmployeeRequest
import com.learning.blog.model.Employee
import com.learning.blog.model.EmployeeResponse
import com.learning.blog.model.UpdateEmployeeRequest
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
    fun getAllEmployees(): ResponseEntity<APIResponse<List<EmployeeResponse>>> {
        val employeeResponse = employees.map { EmployeeResponse.fromEmployee(it) }
        return if (employeeResponse.isNotEmpty()) {
            ResponseEntity.ok(APIResponse(success = true, message = "Fetch employee data", data = employeeResponse))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(APIResponse(success = false, message = "No data found", data = null))
        }

    }

    @GetMapping("/{id}")
    fun getEmployeeById(@PathVariable id: Long): ResponseEntity<APIResponse<EmployeeResponse>> {
        val employee = employees.find { it.id == id }
        return if (employee != null) {
            ResponseEntity.ok(
                APIResponse(
                    success = true,
                    message = "Employee found successfully",
                    data = EmployeeResponse.fromEmployee(employee)
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(APIResponse(success = false, message = "Employee with id $id not found", data = null))
        }
    }

    @PostMapping
    fun createEmployee(@RequestBody request: CreateEmployeeRequest): ResponseEntity<APIResponse<EmployeeResponse>> {
        val newEmployee = Employee(
            id = currentId++,
            name = request.name,
            email = request.email,
            password = request.password,
            department = request.department
        )
        return ResponseEntity.created(URI.create("api/employees/{id}")).body(
            APIResponse(
                success = true,
                message = "Employee populated",
                data = EmployeeResponse.fromEmployee(newEmployee)
            )
        )
    }

    @PutMapping("/{id}")
    fun updateEmployee(
        @PathVariable id: Long,
        @RequestBody request: UpdateEmployeeRequest
    ): ResponseEntity<APIResponse<EmployeeResponse>> {
        val index = employees.indexOfFirst { it.id == id }
        if (index == -1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(APIResponse(success = false, message = "Employee id with $id not found", data = null))
        }
        val employee = employees[index]
        val updatedEmployee = employee.copy(name = request.name, email = request.email, department = request.department)
        employees[index] = updatedEmployee
        return ResponseEntity.ok().body(
            APIResponse(
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
    ): ResponseEntity<APIResponse<EmployeeResponse>> {
        val index = employees.indexOfFirst { it.id == id }

        if (index == -1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(APIResponse(success = false, message = "Employee id with $id not found"))

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
            APIResponse(
                success = true,
                message = "Employee updated successfully",
                data = EmployeeResponse.fromEmployee(employee)
            )
        )

    }


    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id: Long): ResponseEntity<APIResponse<String>> {
        val deleted = employees.removeIf { it.id == id }
        if (deleted) {
            return ResponseEntity.ok(
                APIResponse(
                    success = true,
                    message = "Employee  removed successfully",
                    data = "Employee id $id deleted successfully"
                )
            )
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(APIResponse(success = false, message = "Employee id $id not found", data = null))
    }

    @GetMapping("/search")
    fun searchEmployeeByName(@RequestParam name: String): ResponseEntity<APIResponse<List<EmployeeResponse>>>{
        val matchEmployees= employees.filter { it.name.contains(name, ignoreCase = true) }
        val employeeResponse=matchEmployees.map{ EmployeeResponse.fromEmployee(it) }

        return if(employeeResponse.isNotEmpty()){
            ResponseEntity.ok(APIResponse(success = true, message = "Fetch employee data", data = employeeResponse))
        }else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIResponse(success = false, message = "No data found", data = null))

        }

    }

    fun employeeSearch(@RequestParam query: String?=null): ResponseEntity<APIResponse<List<EmployeeResponse>>>
    {
        val matchEmployees=if(query.isNullOrEmpty()){
            employees
        }else{
            employees.filter { it.name.contains(query, ignoreCase = true)||it.department.contains(query, ignoreCase = true)||it.email.contains(query, ignoreCase = true) }
        }
        val employeeResponse=matchEmployees.map{ EmployeeResponse.fromEmployee(it) }
        return ResponseEntity.ok(APIResponse(success = true, message = "Fetch employee data", data = employeeResponse))
    }

    @GetMapping("/search")
    fun searchEmployees(@RequestParam query: String?=null): ResponseEntity<APIResponse<List<EmployeeResponse>>> {

        val matchEmployees = if (query == null) {
            employees
        } else {
            employees.filter {
                it.name.contains(query, ignoreCase = true) || it.department.contains(
                    query,
                    ignoreCase = true
                ) || it.email.contains(query, ignoreCase = true)
            }
        }
        val employeeResponse = matchEmployees.map { EmployeeResponse.fromEmployee(it) }

        return ResponseEntity.ok(APIResponse(success = true, message = "Fetch employee data", data = employeeResponse))


    }

}