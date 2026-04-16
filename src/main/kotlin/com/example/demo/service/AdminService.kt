package com.example.demo.service

import com.example.demo.entity.Department
import com.example.demo.entity.User
import com.example.demo.enums.Role
import com.example.demo.repository.DepartmentRepository
import com.example.demo.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val userRepository: UserRepository,
    private val departmentRepository: DepartmentRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createUser(
        email: String,
        password: String,
        name: String,
        role: String,
        departmentId: Long?
    ): User {
        val department = departmentId?.let {
            departmentRepository.findById(it).orElseThrow {
                RuntimeException("Department not found")
            }
        }

        val user = User(
            email = email,
            password = passwordEncoder.encode(password),
            name = name,
            role = Role.valueOf(role.uppercase()),
            department = department
        )

        return userRepository.save(user)
    }

    fun createDepartment(name: String, description: String?): Department {
        val department = Department(
            name = name,
            description = description
        )
        return departmentRepository.save(department)
    }

    fun changeUserRole(userId: Long, role: String): User {
        val user = userRepository.findById(userId)
            .orElseThrow { RuntimeException("User not found") }

        user.role = Role.valueOf(role.uppercase())
        return userRepository.save(user)
    }

    fun changeUserDepartment(userId: Long, departmentId: Long): User {
        val user = userRepository.findById(userId)
            .orElseThrow { RuntimeException("User not found") }

        val department = departmentRepository.findById(departmentId)
            .orElseThrow { RuntimeException("Department not found") }

        user.department = department
        return userRepository.save(user)
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }
    fun assignDepartment(userId: Long, departmentId: Long): User {
        val user = userRepository.findById(userId)
            .orElseThrow { RuntimeException("User not found") }

        val department = departmentRepository.findById(departmentId)
            .orElseThrow { RuntimeException("Department not found") }

        user.department = department

        return userRepository.save(user)
    }
}