package com.learning.crudtwo

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class EmployeeService(private val repository: EmployeeRepository) {

    fun getAllEmployees():List<Employee> = repository.findAll()

    fun getEmployeeById(id:Long): Employee?{
        return repository.findById(id).orElse(null)
    }

    fun registerEmployee(employee:Employee): Employee = repository.save(employee)

    fun updateEmployee(id:Long,employee: Employee): Employee?{
        if(repository.existsById(id)){
            val updatedEmployee=employee.copy(id)
            return repository.save(updatedEmployee)
        }else{
            return null
        }
    }

    fun deleteEmployeeById(id:Long):Boolean{
        return if (repository.existsById(id)){
            repository.deleteById(id)
            true
        }else{
            false
        }
    }
}