package com.example.demo.controller

import com.example.demo.dto.request.CreateTaskRequest
import com.example.demo.dto.request.UpdateTaskRequest
import com.example.demo.dto.response.TaskResponse
import com.example.demo.service.TaskService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val taskService: TaskService
) {

    @GetMapping
    fun getTasks(): List<TaskResponse> {
        return taskService.getAll()
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Long): TaskResponse {
        return taskService.getById(id)
    }

    @PostMapping
    fun createTask(
        @RequestBody request: CreateTaskRequest,
        httpRequest: HttpServletRequest
    ): TaskResponse {
        return taskService.create(request, httpRequest)
    }

    @PutMapping("/{id}")
    fun updateTask(
        @PathVariable id: Long,
        @RequestBody request: UpdateTaskRequest
    ): TaskResponse {
        return taskService.update(id, request)
    }
}