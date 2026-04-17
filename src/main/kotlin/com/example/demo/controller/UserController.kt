package com.example.demo.controller

import com.example.demo.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getAllUsers(): ResponseEntity<Any> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<Any> {
        val user = userService.getUserById(id)
            ?: return ResponseEntity.status(404).body("User not found")

        return ResponseEntity.ok(user)
    }

    @PostMapping("/register")
    fun register(
        @RequestParam email: String,
        @RequestParam password: String,
        @RequestParam name: String
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(
            userService.createUser(email, password, name)
        )
    }
    @PostMapping("/{id}/change-role")
    fun changeRole(
        @PathVariable id: Long,
        @RequestParam role: String,
        request: HttpServletRequest
    ) = userService.changeRole(id, role, request)

    @PostMapping("/{id}/assign-department")
    fun assignDepartment(
        @PathVariable id: Long,
        @RequestParam departmentId: Long,
        request: HttpServletRequest
    ) = userService.assignDepartment(id, departmentId, request)
}
