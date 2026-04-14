package com.example.demo.controller

import com.example.demo.dto.request.CreateTaskRequest
import com.example.demo.dto.request.UpdateTaskAssigneeRequest
import com.example.demo.dto.request.UpdateTaskDeadlineRequest
import com.example.demo.dto.request.UpdateTaskStatusRequest
import com.example.demo.dto.response.TaskDetailsResponse
import com.example.demo.dto.response.TaskResponse
import com.example.demo.enums.TaskPriority
import com.example.demo.service.TaskService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Задачи", description = "Методы для создания, просмотра и изменения задач")
class TaskController(
    private val taskService: TaskService
) {

    @Operation(
        summary = "Получить список задач",
        description = "Возвращает список всех задач с возможностью фильтрации"
    )
    @GetMapping
    fun getTasks(
        @RequestParam(required = false) assigneeId: Long?,
        @RequestParam(required = false) priority: TaskPriority?,
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) startDate: LocalDateTime?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) endDate: LocalDateTime?
    ): List<TaskResponse> {
        return taskService.getTasksByFilters(
            assigneeId = assigneeId,
            priority = priority,
            title = title,
            startDate = startDate,
            endDate = endDate
        )
    }

    @Operation(
        summary = "Получить мои задачи",
        description = "Возвращает задачи конкретного исполнителя"
    )
    @GetMapping("/my")
    fun getMyTasks(
        @Parameter(description = "ID текущего пользователя")
        @RequestParam userId: Long
    ): List<TaskResponse> {
        return taskService.getMyTasks(userId)
    }

    @Operation(
        summary = "Получить задачу по id",
        description = "Возвращает полную карточку задачи с комментариями и историей изменений"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Задача найдена"),
            ApiResponse(responseCode = "404", description = "Задача не найдена")
        ]
    )
    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Long): TaskDetailsResponse {
        return taskService.getTaskById(id)
    }

    @Operation(
        summary = "Создать задачу",
        description = "Создает новую задачу и записывает событие в историю"
    )
    @PostMapping
    fun createTask(
        @Valid @RequestBody request: CreateTaskRequest,
        @RequestParam authorId: Long
    ): TaskResponse {
        return taskService.createTask(request, authorId)
    }

    @Operation(
        summary = "Изменить статус задачи",
        description = "Обновляет статус задачи и сохраняет изменение в историю"
    )
    @PatchMapping("/{id}/status")
    fun updateTaskStatus(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateTaskStatusRequest,
        @RequestParam changedByUserId: Long
    ): TaskResponse {
        return taskService.updateTaskStatus(id, request, changedByUserId)
    }

    @Operation(
        summary = "Изменить исполнителя задачи",
        description = "Позволяет переназначить исполнителя задачи"
    )
    @PatchMapping("/{id}/assignee")
    fun updateTaskAssignee(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateTaskAssigneeRequest,
        @RequestParam changedByUserId: Long
    ): TaskResponse {
        return taskService.updateTaskAssignee(id, request, changedByUserId)
    }

    @Operation(
        summary = "Изменить дедлайн задачи",
        description = "Обновляет срок выполнения задачи"
    )
    @PatchMapping("/{id}/deadline")
    fun updateTaskDeadline(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateTaskDeadlineRequest,
        @RequestParam changedByUserId: Long
    ): TaskResponse {
        return taskService.updateTaskDeadline(id, request, changedByUserId)
    }
}