package com.example.demo.dto.response

import com.example.demo.entity.Task

data class TaskResponse(
    val id: Long,
    val title: String,
    val userName: String?,
    val departmentName: String?
)