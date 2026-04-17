package com.example.demo.service

import com.example.demo.dto.request.RegisterRequest
import com.example.demo.dto.response.AuthResponse
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
    private val departmentRepository: DepartmentRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authService: AuthService
) {

    fun register(request: RegisterRequest): AuthResponse {
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

    fun getAll(): List<UserResponse> {
        val departments = departmentRepository.findAll().associateBy { it.id }
        return userRepository.findAll().map { user ->
            val department = user.department?.id?.let { departments[it] }
            UserResponse(
                id = user.id!!,
                name = user.name,
                email = user.email,
                role = user.role.name,
                departmentId = department?.id,
                departmentName = department?.name
            )
        }
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

    private fun toAuthResponse(user: User): AuthResponse {
        val department = user.department
        return AuthResponse(
            id = user.id!!,
            name = user.name,
            email = user.email,
            role = user.role.name,
            departmentId = department?.id,
            departmentName = department?.name
        )
    }

    private fun toUserResponse(user: User): UserResponse {
        val department = user.department
        return UserResponse(
            id = user.id!!,
            name = user.name,
            email = user.email,
            role = user.role.name,
            departmentId = department?.id,
            departmentName = department?.name
        )
    }
}