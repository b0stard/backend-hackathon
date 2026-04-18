package com.example.demo.service

import com.example.demo.dto.response.UserResponse
import com.example.demo.entity.Department
import com.example.demo.entity.User
import com.example.demo.enums.Role
import com.example.demo.repository.DepartmentRepository
import com.example.demo.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val departmentRepository: DepartmentRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getAll(): List<User> {
        return userRepository.findAll()
    }
@Transactional
fun register(
    name: String,
    email: String,
    password: String,
    departmentName: String?
): UserResponse {

    if (userRepository.findByEmail(email) != null) {
        throw RuntimeException("User with this email already exists")
    }

    val department = if (!departmentName.isNullOrBlank()) {
        departmentRepository.findByName(departmentName)
            ?: departmentRepository.save(
                Department(name = departmentName)
            )
    } else {
        null
    }

    val user = User(
        name = name,
        email = email,
        password = passwordEncoder.encode(password),
        role = Role.USER,
        department = department
    )

    return toResponse(userRepository.save(user))
}

    fun changeRole(id: Long, role: String): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found") }

        user.role = Role.valueOf(role.uppercase())

        return toResponse(userRepository.save(user))
    }

    fun assignDepartment(id: Long, departmentId: Long): UserResponse {

        val user = userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found") }

        val department = departmentRepository.findById(departmentId)
            .orElseThrow { RuntimeException("Department not found") }

        user.department = department

        return toResponse(userRepository.save(user))
    }

    private fun toResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id ?: throw RuntimeException("User id is missing"),
            name = user.name,
            email = user.email,
            role = user.role.name,
            departmentId = user.department?.id,
            departmentName = user.department?.name
        )
    }
}