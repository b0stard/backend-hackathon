package com.example.demo.controller

import com.example.demo.dto.response.MetaResponse
import com.example.demo.enums.Role
import com.example.demo.enums.TaskPriority
import com.example.demo.enums.TaskStatus
import com.example.demo.repository.UserRepository
import com.example.demo.service.DepartmentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/meta")
class MetaController(
    private val departmentService: DepartmentService,
    private val userRepository: UserRepository
) {

    @GetMapping
    fun getMeta(): MetaResponse {
        return MetaResponse(
            departments = departmentService.getAll(),
            users = userRepository.findAll(),

            roles = Role.values().map { it.name },
            taskStatuses = TaskStatus.values().map { it.name },
            taskPriorities = TaskPriority.values().map { it.name }
        )
    }
}