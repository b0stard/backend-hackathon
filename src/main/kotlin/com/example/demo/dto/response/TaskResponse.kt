package com.example.demo.dto.response

data class TaskResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val priority: String,
    val deadline: String?,
    val authorId: Long?,
    val authorName: String?,
    val assigneeId: Long?,
    val assigneeName: String?,
    val departmentId: Long?,
    val departmentName: String?
)