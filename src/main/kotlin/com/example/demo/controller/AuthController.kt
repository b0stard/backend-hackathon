package com.example.demo.controller

import com.example.demo.dto.request.LoginRequest
import com.example.demo.dto.response.UserResponse
import com.example.demo.repository.UserRepository
import com.example.demo.service.AuthService
import com.example.demo.service.TaskService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    @PostMapping("/login")
    fun login(
        @RequestBody requestBody: LoginRequest,
        request: HttpServletRequest
    ): ResponseEntity<UserResponse> {
        val user = userRepository.findByEmail(requestBody.email)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        if (!passwordEncoder.matches(requestBody.password, user.password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        request.getSession(true).setAttribute("userId", user.id)

        return ResponseEntity.ok(
            UserResponse(
                id = user.id!!,
                name = user.name,
                email = user.email,
                role = user.role.name,
                departmentId = user.department?.id,
                departmentName = user.department?.name
            )
        )
    }

    @GetMapping("/me")
    fun me(request: HttpServletRequest): ResponseEntity<UserResponse> {
        val user = authService.getCurrentUser(request)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        return ResponseEntity.ok(user)
    }


    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        authService.logout(response)
        return ResponseEntity.ok(mapOf("message" to "Logged out"))
    }
}