package com.example.demo.controller

import com.example.demo.dto.request.CreateTaskRequest
import com.example.demo.dto.request.UpdateTaskAssigneeRequest
import com.example.demo.dto.request.UpdateTaskDeadlineRequest
import com.example.demo.dto.request.UpdateTaskStatusRequest
import com.example.demo.dto.response.TaskResponse
import com.example.demo.enums.TaskPriority
import com.example.demo.service.TaskService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val taskService: TaskService
) {

    @GetMapping
    fun getTasks(
        @RequestParam(required = false) assigneeId: Long?,
        @RequestParam(required = false) priority: TaskPriority?,
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        startDate: LocalDateTime?,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        endDate: LocalDateTime?
    ): List<TaskResponse> {
        return taskService.getTasks(
            assigneeId = assigneeId,
            priority = priority,
            title = title,
            startDate = startDate,
            endDate = endDate
        )
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Long): ResponseEntity<Any> {
        val task = taskService.getTaskById(id)
            ?: return ResponseEntity.status(404).body("Task not found")

        return ResponseEntity.ok(task)
    }

    @GetMapping("/create-meta")
    fun getCreateTaskMeta(): ResponseEntity<Any> {
        return ResponseEntity.ok(taskService.getCreateTaskMeta())
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
        @RequestBody request: UpdateTaskStatusRequest,
        @RequestParam changedByUserId: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, request, changedByUserId))
    }

    @PatchMapping("/{id}/deadline")
    fun updateTaskDeadline(
        @PathVariable id: Long,
        @RequestBody request: UpdateTaskDeadlineRequest,
        @RequestParam changedByUserId: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(taskService.updateTaskDeadline(id, request, changedByUserId))
    }

    @PatchMapping("/{id}/assignee")
    fun updateTaskAssignee(
        @PathVariable id: Long,
        @RequestBody request: UpdateTaskAssigneeRequest,
        @RequestParam changedByUserId: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(taskService.updateTaskAssignee(id, request, changedByUserId))
    }
}