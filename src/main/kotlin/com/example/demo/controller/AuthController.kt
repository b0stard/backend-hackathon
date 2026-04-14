package com.example.demo.controller

import com.example.demo.dto.request.LoginRequest
import com.example.demo.dto.response.AuthResponse
import com.example.demo.service.AuthService
import com.example.demo.service.JwtService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Аутентификация и проверка текущего пользователя")
class AuthController(
    private val authService: AuthService,
    private val jwtService: JwtService
) {

    @PostMapping("/login")
    @Operation(summary = "Логин пользователя")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Успешный логин"),
            ApiResponse(responseCode = "401", description = "Неверный email или пароль")
        ]
    )
    fun login(
        @Valid @RequestBody request: LoginRequest,
        response: HttpServletResponse,
        httpRequest: HttpServletRequest
    ): AuthResponse {
        val user = authService.login(request)
        val token = jwtService.generateToken(user.email)

        val cookie = Cookie("jwt", token)
        cookie.isHttpOnly = true
        cookie.path = "/"
        cookie.maxAge = 24 * 60 * 60
        cookie.secure = !httpRequest.serverName.contains("localhost")
        response.addCookie(cookie)

        return authService.toAuthResponse(user)
    }

    @GetMapping("/me")
    @Operation(summary = "Получить текущего авторизованного пользователя")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Пользователь авторизован"),
            ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
        ]
    )
    fun me(): AuthResponse {
        val user = authService.getCurrentUser()
        return authService.toAuthResponse(user)
    }

    @PostMapping("/logout")
    @Operation(summary = "Выход из системы")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Успешный выход")
        ]
    )
    fun logout(response: HttpServletResponse): Map<String, String> {
        val cookie = Cookie("jwt", null)
        cookie.isHttpOnly = true
        cookie.path = "/"
        cookie.maxAge = 0
        response.addCookie(cookie)

        return mapOf("message" to "Logged out")
    }
}