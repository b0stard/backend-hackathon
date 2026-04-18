package com.example.demo.controller

import com.example.demo.entity.Department
import com.example.demo.repository.DepartmentRepository
import com.example.demo.service.DepartmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/departments")
class DepartmentController(
    private val departmentService: DepartmentService,
    private val departmentRepository: DepartmentRepository
) {

    @GetMapping
    fun getAllDepartments(): ResponseEntity<Any> {
        return ResponseEntity.ok(departmentService.getAllDepartments())
    }

    @GetMapping("/{id}")
    fun getDepartmentById(@PathVariable id: Long): ResponseEntity<Any> {
        val department = departmentService.getDepartmentById(id)
            ?: return ResponseEntity.status(404).body("Department not found")

        return ResponseEntity.ok(department)
    }
    @PostMapping
    fun create(@RequestParam name: String): Department {
        return departmentService.createDepartment(name)
    }
}
