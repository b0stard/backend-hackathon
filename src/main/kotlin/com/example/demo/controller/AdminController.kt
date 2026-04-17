package com.example.demo.controller

import com.example.demo.entity.Department
import com.example.demo.entity.User
import com.example.demo.enums.Role
import com.example.demo.repository.DepartmentRepository
import com.example.demo.repository.UserRepository
import com.example.demo.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import jakarta.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val userRepository: UserRepository,
    private val departmentRepository: DepartmentRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authService: AuthService
) {

    @GetMapping("/users")
    fun getAllUsers(request: HttpServletRequest): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)
            ResponseEntity.ok(userRepository.findAll())
        } catch (e: Exception) {
            ResponseEntity.status(403).body(e.message ?: "Forbidden")
        }
    }

    @PostMapping("/departments")
    fun createDepartment(
        request: HttpServletRequest,
        @RequestParam name: String
    ): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)

            val department = Department(
                name = name
            )

            ResponseEntity.ok(departmentRepository.save(department))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message ?: "Error")
        }
    }

    @GetMapping("/departments")
    fun getAllDepartments(request: HttpServletRequest): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)
            ResponseEntity.ok(departmentRepository.findAll())
        } catch (e: Exception) {
            ResponseEntity.status(403).body(e.message ?: "Forbidden")
        }
    }

    @PostMapping("/users/create")
    fun createUser(
        request: HttpServletRequest,
        @RequestParam name: String,
        @RequestParam email: String,
        @RequestParam password: String,
        @RequestParam role: String,
        @RequestParam(required = false) departmentId: Long?
    ): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)

            val existing = userRepository.findByEmail(email)
            if (existing != null) {
                return ResponseEntity.badRequest().body("User with this email already exists")
            }

            val department = if (departmentId != null) {
                departmentRepository.findById(departmentId)
                    .orElseThrow { RuntimeException("Department not found") }
            } else {
                null
            }

            val user = User(
                name = name,
                email = email,
                password = passwordEncoder.encode(password),
                role = Role.valueOf(role.uppercase()),
                department = department
            )

            ResponseEntity.ok(userRepository.save(user))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message ?: "Error")
        }
    }

    @PostMapping("/users/{userId}/assign-department")
    fun assignDepartment(
        request: HttpServletRequest,
        @PathVariable userId: Long,
        @RequestParam departmentId: Long
    ): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)

            val user = userRepository.findById(userId)
                .orElseThrow { RuntimeException("User not found") }

            val department = departmentRepository.findById(departmentId)
                .orElseThrow { RuntimeException("Department not found") }

            user.department = department
            ResponseEntity.ok(userRepository.save(user))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message ?: "Error")
        }
    }

    @PostMapping("/users/{userId}/change-role")
    fun changeRole(
        request: HttpServletRequest,
        @PathVariable userId: Long,
        @RequestParam role: String
    ): ResponseEntity<Any> {
        return try {
            authService.requireAdmin(request)

            val user = userRepository.findById(userId)
                .orElseThrow { RuntimeException("User not found") }

            user.role = Role.valueOf(role.uppercase())
            ResponseEntity.ok(userRepository.save(user))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message ?: "Error")
        }
    }
}