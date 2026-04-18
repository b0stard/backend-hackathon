package com.example.demo.service

import com.example.demo.dto.request.RegisterRequest
import com.example.demo.entity.User
import com.example.demo.enums.Role
import com.example.demo.repository.DepartmentRepository
import com.example.demo.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val departmentRepository: DepartmentRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getAll(): List<User> = userRepository.findAll()

    fun register(request: RegisterRequest): User {
        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = Role.USER,
            department = null
        )
        return userRepository.save(user)
    }

    fun changeRole(id: Long, role: String): User {
        val user = userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found") }

        user.role = Role.valueOf(role)
        return userRepository.save(user)
    }

    fun assignDepartment(id: Long, departmentId: Long): User {
        val user = userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found") }

        val department = departmentRepository.findById(departmentId)
            .orElseThrow { RuntimeException("Department not found") }

        user.department = department
        return userRepository.save(user)
    }
}