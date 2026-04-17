package com.example.demo.dto.request

data class UpdateTaskRequest(
    val title: String,
    val description: String?,
    val assigneeId: Long?,
    val departmentId: Long?,
    val priority: String,
    val deadline: String?
)