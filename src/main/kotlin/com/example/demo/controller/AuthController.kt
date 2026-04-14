package com.example.demo.controller

import com.example.demo.dto.request.LoginRequest
import com.example.demo.dto.response.AuthResponse
import com.example.demo.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Авторизация", description = "Методы входа в систему")
class AuthController(
    private val authService: AuthService
) {

    @Operation(
        summary = "Вход в систему",
        description = "Позволяет пользователю выполнить вход по email и паролю"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Успешный вход"),
            ApiResponse(responseCode = "400", description = "Ошибка в данных запроса", content = [Content()]),
            ApiResponse(responseCode = "401", description = "Неверные учетные данные", content = [Content()])
        ]
    )
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): AuthResponse {
        return authService.login(request)
    }
}