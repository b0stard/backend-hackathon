package com.example.demo.dto.response

import com.example.demo.entity.Department
import com.example.demo.entity.User

data class MetaResponse(
    val users: List<User>,
    val departments: List<Department>,
    val roles: List<String>
)