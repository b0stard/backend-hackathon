package com.example.demo.controller

import com.example.demo.dto.response.UserResponse
import com.example.demo.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@Tag(name = "Пользователи", description = "Методы для получения пользователей системы")
class UserController(
    private val userService: UserService
) {

    @Operation(
        summary = "Получить список пользователей",
        description = "Возвращает список всех пользователей системы"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Список пользователей успешно получен")
        ]
    )
    @GetMapping
    fun getAllUsers(): List<UserResponse> {
        return userService.getAllUsers()
    }

    @Operation(
        summary = "Получить пользователя по id",
        description = "Возвращает информацию о конкретном пользователе"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Пользователь найден"),
            ApiResponse(responseCode = "404", description = "Пользователь не найден")
        ]
    )
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): UserResponse {
        return userService.getUserById(id)
    }
}