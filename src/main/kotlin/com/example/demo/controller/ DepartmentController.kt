package com.example.demo.controller

import com.example.demo.dto.request.CreateDepartmentRequest
import com.example.demo.service.AuthService
import com.example.demo.service.DepartmentService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/departments")
class DepartmentController(
    private val departmentService: DepartmentService,
    private val authService: AuthService
) {

    @GetMapping
    fun getAll() = departmentService.getAll()

    @PostMapping
    fun create(
        @RequestBody request: CreateDepartmentRequest,
        httpRequest: HttpServletRequest
    ): Any {
        authService.requireAdmin(httpRequest)
        return departmentService.create(request)
    }
}