package com.example.demo.service

import com.example.demo.dto.request.CreateDepartmentRequest
import com.example.demo.dto.response.DepartmentResponse
import com.example.demo.entity.Department
import com.example.demo.repository.DepartmentRepository
import org.springframework.stereotype.Service

@Service
class DepartmentService(
    private val departmentRepository: DepartmentRepository
) {

    fun getAll(): List<DepartmentResponse> {
        return departmentRepository.findAll().map {
            DepartmentResponse(
                id = it.id!!,
                name = it.name
            )
        }
    }

    fun create(request: CreateDepartmentRequest): DepartmentResponse {
        val department = Department(
            name = request.name
        )
        val saved = departmentRepository.save(department)

        return DepartmentResponse(
            id = saved.id!!,
            name = saved.name
        )
    }
}