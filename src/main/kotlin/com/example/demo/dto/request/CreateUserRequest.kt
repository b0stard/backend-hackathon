package com.example.demo.dto.request

data class CreateUserRequest(
    val email: String,
    val password: String,
    val name: String,
    val role: String,
    val departmentId: Long?
)