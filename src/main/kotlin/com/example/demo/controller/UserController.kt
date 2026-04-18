package com.example.demo.controller

import com.example.demo.dto.request.AssignDepartmentRequest
import com.example.demo.dto.request.ChangeRoleRequest
import com.example.demo.dto.request.RegisterRequest
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getAll() = userService.getAll()

    @PostMapping
    fun create(@RequestBody request: RegisterRequest) =
        userService.register(request)

    @PostMapping("/{id}/change-role")
    fun changeRole(
        @PathVariable id: Long,
        @RequestBody request: ChangeRoleRequest
    ) = userService.changeRole(id, request.role)

    @PostMapping("/{id}/assign-department")
    fun assignDepartment(
        @PathVariable id: Long,
        @RequestBody request: AssignDepartmentRequest
    ) = userService.assignDepartment(id, request.departmentId)
}