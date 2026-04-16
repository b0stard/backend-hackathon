package com.example.demo.service

import com.example.demo.entity.User
import com.example.demo.enums.Role
import com.example.demo.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun createUser(email: String, password: String, name: String): User {
        val user = User(
            email = email,
            password = passwordEncoder.encode(password),
            name = name,
            role = Role.USER
        )

        return userRepository.save(user)
    }

    fun checkPassword(rawPassword: String, encodedPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }
}