package com.example.demo.controller

import com.example.demo.dto.request.CreateDepartmentRequest
import com.example.demo.dto.request.CreateUserRequest
import com.example.demo.dto.request.UpdateUserDepartmentRequest
import com.example.demo.dto.request.UpdateUserRoleRequest
import com.example.demo.dto.response.DepartmentResponse
import com.example.demo.dto.response.UserResponse
import com.example.demo.service.AdminService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Администрирование", description = "Методы для управления отделами, пользователями и ролями")
class AdminController(
    private val adminService: AdminService
) {

    @Operation(
        summary = "Создать отдел",
        description = "Создает новый отдел в системе"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Отдел успешно создан"),
            ApiResponse(
                responseCode = "400",
                description = "Некорректный запрос или отдел уже существует",
                content = [Content()]
            )
        ]
    )
    @PostMapping("/departments")
    fun createDepartment(
        @Valid @RequestBody request: CreateDepartmentRequest
    ): DepartmentResponse {
        return adminService.createDepartment(request)
    }

    @Operation(
        summary = "Создать пользователя",
        description = "Создает нового пользователя и назначает ему роль и отдел"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Пользователь успешно создан"),
            ApiResponse(
                responseCode = "400",
                description = "Некорректный запрос или email уже занят",
                content = [Content()]
            ),
            ApiResponse(responseCode = "404", description = "Отдел не найден", content = [Content()])
        ]
    )
    @PostMapping("/users")
    fun createUser(
        @Valid @RequestBody request: CreateUserRequest
    ): UserResponse {
        return adminService.createUser(request)
    }

    @Operation(
        summary = "Изменить роль пользователя",
        description = "Позволяет администратору изменить роль пользователя"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Роль пользователя успешно обновлена"),
            ApiResponse(responseCode = "404", description = "Пользователь не найден", content = [Content()])
        ]
    )
    @PatchMapping("/users/{id}/role")
    fun updateUserRole(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateUserRoleRequest
    ): UserResponse {
        return adminService.updateUserRole(id, request)
    }

    @Operation(
        summary = "Перевести пользователя в другой отдел",
        description = "Изменяет отдел, к которому привязан пользователь"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Отдел пользователя успешно обновлен"),
            ApiResponse(responseCode = "404", description = "Пользователь или отдел не найден", content = [Content()])
        ]
    )
    @PatchMapping("/users/{id}/department")
    fun updateUserDepartment(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateUserDepartmentRequest
    ): UserResponse {
        return adminService.updateUserDepartment(id, request)
    }
}