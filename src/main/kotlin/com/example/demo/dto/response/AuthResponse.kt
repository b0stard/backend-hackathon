package com.example.demo.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Ответ после успешного логина")
data class AuthResponse(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
    val departmentId: Long?,
    val departmentName: String?
)