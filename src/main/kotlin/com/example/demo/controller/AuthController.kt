package com.example.demo.controller

import com.example.demo.dto.request.LoginRequest
import com.example.demo.service.AuthService
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
        @RequestBody request: LoginRequest,
        response: HttpServletResponse
    ): ResponseEntity<Any> {
        return try {
            val result = authService.login(request.email, request.password, response)
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            ResponseEntity.status(401).body(e.message ?: "Unauthorized")
        }
    }

    @GetMapping("/me")
    fun me(request: HttpServletRequest): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(authService.getCurrentUser(request))
        } catch (e: Exception) {
            ResponseEntity.status(401).body(e.message ?: "Not authorized")
        }
    }

    @PostMapping("/logout")
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<Any> {
        authService.logout(request, response)
        return ResponseEntity.ok(mapOf("message" to "Logged out"))
    }
}