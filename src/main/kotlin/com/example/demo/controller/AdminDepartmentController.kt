package com.example.demo.controller

import com.example.demo.entity.Department
import com.example.demo.service.AuthService
import com.example.demo.service.DepartmentService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin/departments")
class AdminDepartmentController(
    private val departmentService: DepartmentService,
    private val authService: AuthService
) {

    @PostMapping
    fun create(
        @RequestBody body: Map<String, String>,
        request: HttpServletRequest
    ): Department {
        authService.requireAdmin(request)

        val name = body["name"] ?: throw RuntimeException("Name required")
        return departmentService.create(name)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody body: Map<String, String>,
        request: HttpServletRequest
    ): Department {
        authService.requireAdmin(request)

        val name = body["name"] ?: throw RuntimeException("Name required")
        return departmentService.update(id, name)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
        request: HttpServletRequest
    ) {
        authService.requireAdmin(request)
        departmentService.delete(id)
    }
}