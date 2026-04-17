package com.example.demo.controller

import com.example.demo.entity.Department
import com.example.demo.service.DepartmentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/departments")
class DepartmentController(
    private val departmentService: DepartmentService
) {

    @GetMapping
    fun getAll(): List<Department> {
        return departmentService.getAll()
    }
}