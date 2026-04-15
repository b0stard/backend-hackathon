package com.example.demo.dto.response

data class CreateTaskMetaResponse(
    val users: List<UserShortResponse>,
    val departments: List<DepartmentResponse>,
    val priorities: List<String>,
    val statuses: List<String>
)