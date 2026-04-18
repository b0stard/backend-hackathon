package com.example.demo.controller

import com.example.demo.dto.request.CreateTaskRequest
import com.example.demo.dto.request.UpdateTaskStatusRequest
import com.example.demo.dto.response.TaskResponse
import com.example.demo.service.TaskService
import org.springframework.http.ResponseEntity
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
    fun getTaskById(@PathVariable id: Long): ResponseEntity<Any> {
        val task = taskService.getTaskById(id)
            ?: return ResponseEntity.status(404).body("Task not found")

        return ResponseEntity.ok(task)
    }

    @PostMapping
    fun createTask(
        @RequestBody request: CreateTaskRequest,
        @RequestParam authorId: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(taskService.createTask(request, authorId))
    }

    @PatchMapping("/{id}/status")
    fun updateTaskStatus(
        @PathVariable id: Long,
        @RequestBody request: UpdateTaskStatusRequest
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, request))
    }
}