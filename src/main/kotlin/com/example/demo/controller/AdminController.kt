package com.example.demo.controller

import com.example.demo.dto.request.CreateDepartmentRequest
import com.example.demo.dto.request.CreateUserRequest
import com.example.demo.service.AdminService
import com.example.demo.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val adminService: AdminService,
    private val authService: AuthService
) {

    @PostMapping("/users")
    fun createUser(
        request: HttpServletRequest,
        @RequestBody body: CreateUserRequest
    ): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)
            ResponseEntity.ok(
                adminService.createUser(
                    email = body.email,
                    password = body.password,
                    name = body.name,
                    role = body.role,
                    departmentId = body.departmentId
                )
            )
        } catch (e: RuntimeException) {
            val status = if (e.message == "Forbidden") 403 else 401
            ResponseEntity.status(status).body(e.message)
        }
    }

    @PostMapping("/departments")
    fun createDepartment(
        request: HttpServletRequest,
        @RequestBody body: CreateDepartmentRequest
    ): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)
            ResponseEntity.ok(adminService.createDepartment(body.name, body.description))
        } catch (e: RuntimeException) {
            val status = if (e.message == "Forbidden") 403 else 401
            ResponseEntity.status(status).body(e.message)
        }
    }

    @PatchMapping("/users/{id}/role")
    fun changeRole(
        request: HttpServletRequest,
        @PathVariable id: Long,
        @RequestParam role: String
    ): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)
            ResponseEntity.ok(adminService.changeUserRole(id, role))
        } catch (e: RuntimeException) {
            val status = if (e.message == "Forbidden") 403 else 400
            ResponseEntity.status(status).body(e.message)
        }
    }

    @PatchMapping("/users/{id}/department")
    fun changeDepartment(
        request: HttpServletRequest,
        @PathVariable id: Long,
        @RequestParam departmentId: Long
    ): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)
            ResponseEntity.ok(adminService.changeUserDepartment(id, departmentId))
        } catch (e: RuntimeException) {
            val status = if (e.message == "Forbidden") 403 else 400
            ResponseEntity.status(status).body(e.message)
        }
    }
}