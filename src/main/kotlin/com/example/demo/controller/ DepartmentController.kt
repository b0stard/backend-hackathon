package com.example.demo.controller

import com.example.demo.entity.Department
import com.example.demo.service.AuthService
import com.example.demo.service.DepartmentService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/departments")
class DepartmentController(
    private val departmentService: DepartmentService,
    private val authService: AuthService
) {

    @GetMapping("/user")
    fun getAll(): List<Department> {
        return departmentService.getAll()
    }

    @PostMapping("/user")
    fun create(
        @RequestBody body: Map<String, String>,
        request: HttpServletRequest
    ): Department {
        authService.requireAdmin(request)

        val name = body["name"] ?: throw RuntimeException("Name required")
        return departmentService.create(name)
    }
}