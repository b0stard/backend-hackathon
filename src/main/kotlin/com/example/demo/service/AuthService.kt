package com.example.demo.service

import com.example.demo.dto.response.AuthResponse
import com.example.demo.entity.User
import com.example.demo.enums.Role
import com.example.demo.exception.NotFoundException
import com.example.demo.repository.UserRepository
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(email: String, password: String, response: HttpServletResponse): AuthResponse {
        val user = userRepository.findByEmail(email)
            ?: throw RuntimeException("User not found")

        if (!passwordEncoder.matches(password, user.password)) {
            throw RuntimeException("Wrong password")
        }

        val cookie = Cookie("userId", user.id.toString())
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.maxAge = 60 * 60 * 24

        response.addCookie(cookie)

        return toAuthResponse(user)
    }

    fun getCurrentUser(request: HttpServletRequest): AuthResponse {
        val userId = request.cookies
            ?.find { it.name == "userId" }
            ?.value
            ?.toLongOrNull()
            ?: throw RuntimeException("Not authorized")

        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("User not found") }

        return toAuthResponse(user)
    }

    fun requireAdmin(request: HttpServletRequest): User {
        val userId = request.cookies
            ?.find { it.name == "userId" }
            ?.value
            ?.toLongOrNull()
            ?: throw RuntimeException("Not authorized")

        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("User not found") }

        if (user.role != Role.ADMIN) {
            throw RuntimeException("Forbidden")
        }

        return user
    }

    fun logout(response: HttpServletResponse) {
        val cookie = Cookie("userId", "")
        cookie.path = "/"
        cookie.maxAge = 0
        response.addCookie(cookie)
    }

    private fun toAuthResponse(user: User): AuthResponse {
        return AuthResponse(
            id = user.id,
            name = user.name,
            email = user.email,
            role = user.role.name,
            departmentId = user.department?.id,
            departmentName = user.department?.name
        )
    }
}