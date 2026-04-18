package com.example.demo.controller



import com.example.demo.service.DepartmentService
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/meta")
class MetaController(
    private val userService: UserService,
    private val departmentService: DepartmentService
) {

    @GetMapping
    fun getMeta(): Map<String, Any> {
        return mapOf(
            "users" to userService.getAll(),
            "departments" to departmentService.getAll(),
            "roles" to listOf("ADMIN", "USER")
        )
    }
}