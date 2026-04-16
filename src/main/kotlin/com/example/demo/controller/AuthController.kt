package com.example.demo.controller

import com.example.demo.service.RedisService
import com.example.demo.service.UserService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
    private val redisService: RedisService
) {

    @PostMapping("/login")
    fun login(
        @RequestParam email: String,
        @RequestParam password: String,
        response: HttpServletResponse
    ): ResponseEntity<Any> {

        val user = userService.findByEmail(email)
            ?: return ResponseEntity.status(401).body("User not found")

        if (!userService.checkPassword(password, user.password!!)) {
            return ResponseEntity.status(401).body("Wrong password")
        }

        val sessionId = UUID.randomUUID().toString()

        redisService.saveSession(sessionId, user.id)

        val cookie = Cookie("sessionId", sessionId)
        cookie.isHttpOnly = true
        cookie.path = "/"
        cookie.maxAge = 60 * 60 * 24

        response.addCookie(cookie)

        return ResponseEntity.ok(user)
    }

    @PostMapping("/logout")
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<Any> {

        val sessionId = request.cookies
            ?.find { it.name == "sessionId" }
            ?.value

        if (sessionId != null) {
            redisService.deleteSession(sessionId)
        }

        val cookie = Cookie("sessionId", "")
        cookie.path = "/"
        cookie.maxAge = 0
        response.addCookie(cookie)

        return ResponseEntity.ok("Logged out")
    }
    @PostMapping("/register")
    fun register(
        @RequestParam email: String,
        @RequestParam password: String,
        @RequestParam name: String
    ): ResponseEntity<Any> {

        val user = userService.createUser(email, password, name)

        return ResponseEntity.ok(user)
    }

    @GetMapping("/me")
    fun me(request: HttpServletRequest): ResponseEntity<Any> {

        val sessionId = request.cookies
            ?.find { it.name == "sessionId" }
            ?.value
            ?: return ResponseEntity.status(401).body("Not authorized")

        val userId = redisService.getUserId(sessionId)
            ?: return ResponseEntity.status(401).body("Session expired")

        val user = userService.findById(userId)
            ?: return ResponseEntity.status(404).body("User not found")

        return ResponseEntity.ok(user)
    }
}