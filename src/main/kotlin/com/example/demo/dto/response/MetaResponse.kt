package com.example.demo.dto.response

import com.example.demo.entity.Department
import com.example.demo.entity.User

data class MetaResponse(
    val departments: List<Department>,
    val users: List<User>,
    val roles: List<String>,
    val taskStatuses: List<String>,
    val taskPriorities: List<String>
)