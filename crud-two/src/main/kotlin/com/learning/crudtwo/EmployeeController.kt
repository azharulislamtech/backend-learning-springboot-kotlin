package com.learning.crudtwo

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/employees")
class EmployeeController(private val service: EmployeeService) {


    @GetMapping()
    fun getAllEmployees()=service.getAllEmployees()

    @PostMapping()
    fun registerEmployee(@RequestBody employee: Employee)=service.registerEmployee(employee)

    @GetMapping("/{id}")
    fun findEmployeeById(@PathVariable id:Long): ResponseEntity<Employee>{
        val employee=service.getEmployeeById(id)
        return if(employee!=null){
            ResponseEntity.ok(employee)
        }else{
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/{id}")
    fun updateEmployee(@PathVariable id:Long,@RequestBody employee: Employee): ResponseEntity<Employee>{
        val updatedEmployee=service.updateEmployee(id,employee)
        return if(updatedEmployee!=null){
            ResponseEntity.ok(updatedEmployee)
        }else{
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id:Long): ResponseEntity<Void>{
        return if(service.deleteEmployeeById(id)){
            ResponseEntity.noContent().build()
        }else{
            ResponseEntity.notFound().build()
        }
    }
}
