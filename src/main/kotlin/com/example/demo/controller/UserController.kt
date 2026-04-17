package com.example.demo.controller

import com.example.demo.dto.request.RegisterRequest
import com.example.demo.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest) =
        userService.register(request)

    @GetMapping
    fun getAll() =
        userService.getAll()

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