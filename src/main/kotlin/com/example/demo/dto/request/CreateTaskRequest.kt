package com.example.demo.dto.request

data class CreateTaskRequest(
    val title: String,
    val description: String?,
    val assigneeId: Long?,
    val departmentId: Long?,
    val priority: String,
    val deadline: String?
)