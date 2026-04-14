package com.example.demo.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Schema(description = "Запрос на логин")
data class LoginRequest(
    @field: Email
    @field: NotBlank
    @Schema(description = "Email пользователя", example = "admin@company.ru")
    val email: String,

    @field: NotBlank
    @Schema(description = "Пароль", example = "123456")
    val password: String
)