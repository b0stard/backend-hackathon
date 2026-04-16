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

    @GetMapping("/users")
    fun getAllUsers(request: HttpServletRequest): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)
            ResponseEntity.ok(adminService.getAllUsers())
        } catch (e: RuntimeException) {
            val status = if (e.message == "Forbidden") 403 else 401
            ResponseEntity.status(status).body(e.message ?: "Unauthorized")
        }
    }

    @PostMapping("/users")
    fun createUser(
        request: HttpServletRequest,
        @RequestBody body: CreateUserRequest
    ): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)

            val user = adminService.createUser(
                email = body.email,
                password = body.password,
                name = body.name,
                role = body.role,
                departmentId = body.departmentId
            )

            ResponseEntity.ok(user)
        } catch (e: RuntimeException) {
            val status = if (e.message == "Forbidden") 403 else 400
            ResponseEntity.status(status).body(e.message ?: "Error")
        }
    }

    @PostMapping("/departments")
    fun createDepartment(
        request: HttpServletRequest,
        @RequestBody body: CreateDepartmentRequest
    ): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)

            val department = adminService.createDepartment(
                name = body.name,
                description = body.description
            )

            ResponseEntity.ok(department)
        } catch (e: RuntimeException) {
            val status = if (e.message == "Forbidden") 403 else 400
            ResponseEntity.status(status).body(e.message ?: "Error")
        }
    }

    @PatchMapping("/users/{id}/role")
    fun changeUserRole(
        request: HttpServletRequest,
        @PathVariable id: Long,
        @RequestParam role: String
    ): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)
            ResponseEntity.ok(adminService.changeUserRole(id, role))
        } catch (e: RuntimeException) {
            val status = if (e.message == "Forbidden") 403 else 400
            ResponseEntity.status(status).body(e.message ?: "Error")
        }
    }

    @PatchMapping("/users/{id}/department")
    fun changeUserDepartment(
        request: HttpServletRequest,
        @PathVariable id: Long,
        @RequestParam departmentId: Long
    ): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)
            ResponseEntity.ok(adminService.changeUserDepartment(id, departmentId))
        } catch (e: RuntimeException) {
            val status = if (e.message == "Forbidden") 403 else 400
            ResponseEntity.status(status).body(e.message ?: "Error")
        }
    }
}