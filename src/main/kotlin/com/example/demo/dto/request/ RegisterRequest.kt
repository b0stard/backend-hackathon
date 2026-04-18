package com.example.demo.dto.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)