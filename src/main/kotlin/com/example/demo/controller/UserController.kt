package com.example.demo.controller

import com.example.demo.dto.request.RegisterRequest
import com.example.demo.dto.response.AuthResponse
import com.example.demo.entity.User
import com.example.demo.enums.Role
import com.example.demo.repository.DepartmentRepository
import com.example.demo.repository.UserRepository
import com.example.demo.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userRepository: UserRepository,
    private val departmentRepository: DepartmentRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): AuthResponse {
        if (userRepository.findByEmail(request.email) != null) {
            throw RuntimeException("User with this email already exists")
        }

        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = Role.USER,
            department = null
        )

        return toAuthResponse(userRepository.save(user))
    }

    @GetMapping
    fun getAll(): List<User> {
        return userRepository.findAll()
    }

    @PostMapping("/{id}/change-role")
    fun changeRole(
        @PathVariable id: Long,
        @RequestParam role: String,
        request: HttpServletRequest
    ): User {
        authService.requireAdmin(request)

        val user = userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found") }

        user.role = Role.valueOf(role.uppercase())
        return userRepository.save(user)
    }

    @PostMapping("/{id}/assign-department")
    fun assignDepartment(
        @PathVariable id: Long,
        @RequestParam departmentId: Long,
        request: HttpServletRequest
    ): User {
        authService.requireAdmin(request)

        val user = userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found") }

        val department = departmentRepository.findById(departmentId)
            .orElseThrow { RuntimeException("Department not found") }

        user.department = department
        return userRepository.save(user)
    }

    private fun toAuthResponse(user: User): AuthResponse {
        return AuthResponse(
            id = user.id!!,
            name = user.name,
            email = user.email,
            role = user.role.name,
            departmentId = user.department?.id,
            departmentName = user.department?.name
        )
    }
}