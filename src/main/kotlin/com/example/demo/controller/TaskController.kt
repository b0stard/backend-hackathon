package com.example.demo.controller

import com.example.demo.dto.request.CreateTaskRequest
import com.example.demo.dto.request.UpdateTaskRequest
import com.example.demo.service.TaskService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val taskService: TaskService
) {

    @GetMapping
    fun getAll() = taskService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) =
        taskService.getById(id)

    @PostMapping
    fun create(
        @RequestBody request: CreateTaskRequest,
        httpRequest: HttpServletRequest
    ) = taskService.create(request, httpRequest)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: UpdateTaskRequest
    ) = taskService.update(id, request)
}