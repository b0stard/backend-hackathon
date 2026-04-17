package com.example.demo.service

import com.example.demo.dto.response.MetaResponse
import com.example.demo.enums.Role
import com.example.demo.enums.TaskPriority
import com.example.demo.enums.TaskStatus
import org.springframework.stereotype.Service

@Service
class MetaService(
    private val departmentService: DepartmentService,
    private val userService: UserService
) {

    fun getMeta(): MetaResponse {
        return MetaResponse(
            departments = departmentService.getAll(),
            users = userService.getAll(),
            roles = Role.entries.map { it.name },
            taskStatuses = TaskStatus.entries.map { it.name },
            taskPriorities = TaskPriority.entries.map { it.name }
        )
    }
}