package com.example.demo.controller

import com.example.demo.dto.request.CreateTaskRequest
import com.example.demo.dto.request.UpdateTaskRequest
import com.example.demo.dto.response.TaskResponse
import com.example.demo.service.TaskService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val taskService: TaskService
) {

    @GetMapping
    fun getAll(): List<TaskResponse> {
        return taskService.getAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): TaskResponse {
        return taskService.getById(id)
    }

    @PostMapping
    fun create(
        @RequestBody request: CreateTaskRequest,
        httpRequest: HttpServletRequest
    ): TaskResponse {
        return taskService.create(request, httpRequest)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: UpdateTaskRequest
    ): TaskResponse {
        return taskService.update(id, request)
    }
}