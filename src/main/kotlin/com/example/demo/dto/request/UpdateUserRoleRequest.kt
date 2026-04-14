package com.example.demo.dto.request

import com.example.demo.enums.Role
import jakarta.validation.constraints.NotNull

data class UpdateUserRoleRequest(
    @field: NotNull(message = "Роль обязательна")
    val role: Role
)