package com.example.demo.dto.response

data class MetaResponse(
    val departments: List<DepartmentResponse>,
    val users: List<UserResponse>,
    val roles: List<String>,
    val taskStatuses: List<String>,
    val taskPriorities: List<String>
)