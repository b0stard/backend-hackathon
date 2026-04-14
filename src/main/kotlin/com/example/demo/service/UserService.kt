package com.example.demo.service


import com.example.demo.dto.response.UserResponse
import com.example.demo.entity.User
import org.springframework.stereotype.Service
import com.example.demo.repository.UserRepository

@Service
class UserService (
private val userRepository: UserRepository
) {
    fun getAllUsers(): List<UserResponse> {
        return userRepository.findAll().map { it.toResponse() }
    }

    fun getUserById(id: Long): UserResponse {
        val user = userRepository.findByIdWithDepartment(id)
            .orElseThrow { RuntimeException("Пользователь не найден") }

        return user.toResponse()
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