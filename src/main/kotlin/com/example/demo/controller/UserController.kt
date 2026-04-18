package com.example.demo.controller

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
    fun create(
        @RequestParam name: String,
        @RequestParam email: String,
        @RequestParam password: String,
        @RequestParam(required = false) departmentName: String?
    ): UserResponse {
        return userService.register(name, email, password, departmentName)
    }

    @PostMapping("/register")
    fun register(
        @RequestParam name: String,
        @RequestParam email: String,
        @RequestParam password: String,
        @RequestParam(required = false) departmentName: String?
    ): UserResponse {
        return userService.register(name, email, password, departmentName)
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