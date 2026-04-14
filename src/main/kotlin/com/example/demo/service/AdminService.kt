package com.example.demo.service

import com.example.demo.dto.request.CreateDepartmentRequest
import com.example.demo.dto.request.CreateUserRequest
import com.example.demo.dto.request.UpdateUserDepartmentRequest
import com.example.demo.dto.request.UpdateUserRoleRequest
import com.example.demo.dto.response.DepartmentResponse
import com.example.demo.dto.response.UserResponse
import com.example.demo.entity.Department
import com.example.demo.entity.User
import com.example.demo.exception.BadRequestException
import com.example.demo.exception.NotFoundException
import com.example.demo.repository.DepartmentRepository
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminService(
    private val departmentRepository: DepartmentRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun createDepartment(request: CreateDepartmentRequest): DepartmentResponse {
        if (departmentRepository.existsByName(request.name)) {
            throw BadRequestException("Отдел с таким названием уже существует")
        }

        val department = Department(
            name = request.name,
            description = request.description
        )

        val savedDepartment = departmentRepository.save(department)

        return DepartmentResponse(
            id = savedDepartment.id,
            name = savedDepartment.name
        )
    }

    @Transactional
    fun createUser(request: CreateUserRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw BadRequestException("Пользователь с таким email уже существует")
        }

        val department = request.departmentId?.let { departmentId ->
            departmentRepository.findById(departmentId)
                .orElseThrow { NotFoundException("Отдел не найден") }
        }

        val user = User(
            name = request.name,
            email = request.email,
            password = request.password,
            role = request.role,
            department = department,
            isActive = true
        )

        val savedUser = userRepository.save(user)

        return savedUser.toResponse()
    }

    @Transactional
    fun updateUserRole(userId: Long, request: UpdateUserRoleRequest): UserResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("Пользователь не найден") }

        val updatedUser = user.copy(role = request.role)

        val savedUser = userRepository.save(updatedUser)

        return savedUser.toResponse()
    }

    @Transactional
    fun updateUserDepartment(userId: Long, request: UpdateUserDepartmentRequest): UserResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("Пользователь не найден") }

        val department = departmentRepository.findById(request.departmentId)
            .orElseThrow { NotFoundException("Отдел не найден") }

        val updatedUser = user.copy(department = department)

        val savedUser = userRepository.save(updatedUser)

        return savedUser.toResponse()
    }

    private fun User.toResponse(): UserResponse {
        return UserResponse(
            id = this.id,
            name = this.name,
            email = this.email,
            role = this.role.name,
            departmentId = this.department?.id,
            departmentName = this.department?.name
        )
    }
}