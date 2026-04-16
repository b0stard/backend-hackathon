package com.example.demo.controller

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
        @RequestParam email: String,
        @RequestParam password: String,
        response: HttpServletResponse
    ): ResponseEntity<Any> {

        return try {
            authService.login(email, password, response)
            ResponseEntity.ok("Logged in")
        } catch (e: Exception) {
            ResponseEntity.status(401).body(e.message)
        }
    }

    @GetMapping("/me")
    fun me(request: HttpServletRequest): ResponseEntity<Any> {
        val cookie = request.cookies?.find { it.name == "userId" }
            ?: return ResponseEntity.status(401).body("Not authorized")

        val user = authService.getMe(cookie.value.toLong())
        return ResponseEntity.ok(user)
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        authService.logout(response)
        return ResponseEntity.ok("Logged out")
    }
}