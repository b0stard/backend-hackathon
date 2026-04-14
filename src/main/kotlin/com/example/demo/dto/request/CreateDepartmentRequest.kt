package com.example.demo.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateDepartmentRequest(
    @field: NotBlank(message = "Название отдела обязательно")
    val name: String,

    val description: String? = null
)