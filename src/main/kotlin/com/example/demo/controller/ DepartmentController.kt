package com.example.demo.controller

import com.example.demo.entity.Department
import com.example.demo.service.DepartmentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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

    @PostMapping
    fun create(@RequestBody body: Map<String, String>): Department {
        val name = body["name"] ?: throw RuntimeException("Name required")
        return departmentService.create(name)
    }
}