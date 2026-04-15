package com.example.demo.controller

import com.example.demo.dto.response.DepartmentResponse
import com.example.demo.repository.DepartmentRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/departments")
@Tag(name = "Departments", description = "Справочник отделов")
class DepartmentController(
    private val departmentRepository: DepartmentRepository
) {

    @GetMapping
    @Operation(summary = "Получить список отделов")
    fun getAll(): List<DepartmentResponse> {
        return departmentRepository.findAll().map {
            DepartmentResponse(
                id = it.id,
                name = it.name
            )
        }
    }
}