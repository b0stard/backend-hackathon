package com.example.demo.service

import com.example.demo.dto.response.MetaResponse
import com.example.demo.enums.Role
import com.example.demo.repository.DepartmentRepository
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class MetaService(
    private val departmentRepository: DepartmentRepository,
    private val userRepository: UserRepository,
) {

    fun getMeta(): MetaResponse {
        return MetaResponse(
            users = userRepository.findAll(),
            departments = departmentRepository.findAll(),
            roles = Role.values().map { it.name }
        )
    }
}