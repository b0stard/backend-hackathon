package com.example.demo.controller

import com.example.demo.dto.request.RegisterRequest
import com.example.demo.dto.response.UserResponse
import com.example.demo.entity.User
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getAll(): List<User> {
        return userService.getAll()
    }

    @PostMapping
    fun create(@RequestBody request: RegisterRequest): UserResponse {
        return userService.create(request)
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): UserResponse {
        return userService.create(request)
    }

    @PostMapping("/{id}/change-role")
    fun changeRole(
        @PathVariable id: Long,
        @RequestParam role: String
    ): UserResponse {
        return userService.changeRole(id, role)
    }

    @PostMapping("/{id}/assign-department")
    fun assignDepartment(
        @PathVariable id: Long,
        @RequestParam departmentId: Long
    ): UserResponse {
        return userService.assignDepartment(id, departmentId)
    }
}