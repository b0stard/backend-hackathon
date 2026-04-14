package com.example.demo.dto.response

data class AuthResponse(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
    val departmentId: Long?,
    val departmentName: String?,
    val token: String
)