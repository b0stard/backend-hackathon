package com.example.demo.controller

import com.example.demo.service.AuthService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(
        @RequestParam email: String,
        @RequestParam password: String,
        response: HttpServletResponse
    ): ResponseEntity<Any> {
        return try {
            val (user, sessionId) = authService.login(email, password)

            val cookie = Cookie("sessionId", sessionId)
            cookie.isHttpOnly = true
            cookie.path = "/"
            cookie.maxAge = 60 * 60 * 24

            response.addCookie(cookie)

            ResponseEntity.ok(user)
        } catch (e: RuntimeException) {
            ResponseEntity.status(401).body(e.message ?: "Unauthorized")
        }
    }

    @PostMapping("/logout")
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<Any> {
        val sessionId = request.cookies
            ?.find { it.name == "sessionId" }
            ?.value

        authService.logout(sessionId)

        val cookie = Cookie("sessionId", "")
        cookie.path = "/"
        cookie.maxAge = 0
        response.addCookie(cookie)

        return ResponseEntity.ok(mapOf("message" to "Logged out"))
    }

    @GetMapping("/me")
    fun me(request: HttpServletRequest): ResponseEntity<Any> {
        return try {
            val user = authService.getCurrentUser(request)
            ResponseEntity.ok(user)
        } catch (e: RuntimeException) {
            ResponseEntity.status(401).body(e.message ?: "Unauthorized")
        }
    }
}