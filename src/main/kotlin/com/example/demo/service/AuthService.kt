package com.example.demo.service

import com.example.demo.dto.request.LoginRequest
import com.example.demo.dto.response.AuthResponse
import com.example.demo.entity.User
import com.example.demo.exception.NotFoundException
import com.example.demo.exception.UnauthorizedException
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {

    fun login(request: LoginRequest): AuthResponse {
        val user = userRepository.findByEmail(request.email)
            .orElseThrow { UnauthorizedException("Пользователь с таким email не найден") }

        validateCredentials(user, request.password)

        val token = jwtService.generateToken(user)

        return AuthResponse(
            id = user.id,
            name = user.name,
            email = user.email,
            role = user.role.name,
            departmentId = user.department?.id,
            departmentName = user.department?.name,
            token = token
        )
    }

    private fun validateCredentials(user: User, rawPassword: String) {
        if (user.password != rawPassword) {
            throw UnauthorizedException("Неверный email или пароль")
        }
    }
}