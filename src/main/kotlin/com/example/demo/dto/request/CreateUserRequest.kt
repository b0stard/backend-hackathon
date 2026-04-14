package com.example.demo.dto.request

import com.example.demo.enums.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateUserRequest(
    @field: NotBlank(message = "Имя обязательно")
    val name: String,

    @field: Email(message = "Некорректный email")
    @field: NotBlank(message = "Email обязателен")
    val email: String,

    @field: NotBlank(message = "Пароль обязателен")
    val password: String,

    @field: NotNull(message = "Роль обязательна")
    val role: Role,

    val departmentId: Long? = null
)