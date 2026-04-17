package com.example.demo.service

import com.example.demo.dto.response.UserResponse
import com.example.demo.entity.User
import com.example.demo.enums.Role
import com.example.demo.exception.NotFoundException
import com.example.demo.repository.DepartmentRepository
import com.example.demo.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authService: AuthService,
    private val departmentRepository: DepartmentRepository
) {

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
    fun changeRole(id: Long, role: String, request: HttpServletRequest): UserResponse {
        authService.requireAdmin(request)

        val user = userRepository.findById(id)
            .orElseThrow { NotFoundException("User not found") }

        user.role = Role.valueOf(role.uppercase())

        return toUserResponse(userRepository.save(user))
    }
    fun assignDepartment(id: Long, departmentId: Long, request: HttpServletRequest): UserResponse {
        authService.requireAdmin(request)

        val user = userRepository.findById(id)
            .orElseThrow { NotFoundException("User not found") }

        val department = departmentRepository.findById(departmentId)
            .orElseThrow { NotFoundException("Department not found") }

        user.department = department

        return toUserResponse(userRepository.save(user))
    }
    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun createUser(email: String, password: String, name: String): User {
        val existing = userRepository.findByEmail(email)
        if (existing != null) {
            throw RuntimeException("User with this email already exists")
        }

        val user = User(
            email = email,
            password = passwordEncoder.encode(password),
            name = name,
            role = Role.USER
        )

        return userRepository.save(user)
    }

    fun createAdmin(email: String, password: String, name: String): User {
        val existing = userRepository.findByEmail(email)
        if (existing != null) {
            throw RuntimeException("Admin already exists")
        }

        val admin = User(
            email = email,
            password = passwordEncoder.encode(password),
            name = name,
            role = Role.ADMIN
        )

        return userRepository.save(admin)
    }

    fun checkPassword(rawPassword: String, encodedPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }
    private fun toUserResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id!!,
            name = user.name,
            email = user.email,
            role = user.role.name,
            departmentId = user.department?.id,
            departmentName = user.department?.name
        )
    }
}
