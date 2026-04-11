package com.learning.crudtwo.service

import com.learning.crudtwo.dto.request.PatchEmployeeRequest
import com.learning.crudtwo.dto.request.UpdateEmployeeRequest
import com.learning.crudtwo.repository.EmployeeRepository
import com.learning.crudtwo.model.Employee
import org.springframework.stereotype.Service

@Service
class EmployeeService(private val repository: EmployeeRepository) {

    fun getAllEmployees(): List<Employee> = repository.findAll()

    fun getEmployeeById(id: Long): Employee? = repository.findById(id).orElse(null)

    fun registerEmployee(employee: Employee): Employee = repository.save(employee)

    fun updateEmployee(id: Long, employee: UpdateEmployeeRequest): Employee? {
        val existing = repository.findById(id).orElse(null) ?: return null
        return repository.save(existing.copy(name = employee.name, email = employee.email))
    }

    fun patchEmployee(id: Long, employee: PatchEmployeeRequest): Employee? {
        val existing = repository.findById(id).orElse(null) ?: return null

        return repository.save(existing.copy(
            name = employee.name ?: existing.name,
            email = employee.email ?: existing.email
        ))
    }

    fun deleteEmployeeById(id: Long): Boolean {
        return if (repository.existsById(id)) {
            repository.deleteById(id)
            true
        } else {
            false
        }
    }
}
