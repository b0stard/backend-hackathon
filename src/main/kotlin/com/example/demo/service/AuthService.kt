package com.example.demo.service

import com.example.demo.dto.request.LoginRequest
import com.example.demo.dto.response.AuthResponse
import com.example.demo.entity.User
import com.example.demo.exception.UnauthorizedException
import com.example.demo.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository
) {

    fun login(request: LoginRequest): User {
        val user = userRepository.findByEmail(request.email)
            .orElseThrow { UnauthorizedException("Неверный email или пароль") }

        validateCredentials(user, request.password)

        return user
    }

    fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw UnauthorizedException("Пользователь не авторизован")

        val email = authentication.name

        return userRepository.findByEmail(email)
            .orElseThrow { UnauthorizedException("Пользователь не найден") }
    }

    fun toAuthResponse(user: User): AuthResponse {
        return AuthResponse(
            id = user.id,
            name = user.name,
            email = user.email,
            role = user.role.name,
            departmentId = user.department?.id,
            departmentName = user.department?.name
        )
    }

    private fun validateCredentials(user: User, rawPassword: String) {
        if (user.password != rawPassword) {
            throw UnauthorizedException("Неверный email или пароль")
        }
    }
}