package com.example.demo.dto.request

import jakarta.validation.constraints.NotNull

data class UpdateUserDepartmentRequest(
    @field: NotNull(message = "ID отдела обязателен")
    val departmentId: Long
)