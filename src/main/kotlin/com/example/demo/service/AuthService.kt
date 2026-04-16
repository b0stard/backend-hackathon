package com.example.demo.service

import com.example.demo.entity.User
import com.example.demo.exception.NotFoundException
import com.example.demo.repository.UserRepository
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(email: String, password: String, response: HttpServletResponse) {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException("User not found")

        if (!passwordEncoder.matches(password, user.password)) {
            throw RuntimeException("Wrong password")
        }

        val cookie = Cookie("userId", user.id.toString())
        cookie.path = "/"
        cookie.isHttpOnly = true

        response.addCookie(cookie)
    }

    fun getMe(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { NotFoundException("User not found") }
    }

    fun logout(response: HttpServletResponse) {
        val cookie = Cookie("userId", "")
        cookie.maxAge = 0
        cookie.path = "/"
        response.addCookie(cookie)
    }
}