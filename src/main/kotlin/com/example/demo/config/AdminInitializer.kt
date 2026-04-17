package com.example.demo.config

import com.example.demo.entity.User
import com.example.demo.enums.Role
import com.example.demo.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder

@Bean
fun initAdmin(
    userRepository: UserRepository,
    passwordEncoder: PasswordEncoder
): CommandLineRunner {
    return CommandLineRunner {
        val existing = userRepository.findByEmail("admin@gmail.com")

        if (existing == null) {
            val encodedPassword = passwordEncoder.encode("admin123")
                ?: throw RuntimeException("Failed to encode password")

            val admin = User(
                email = "admin@gmail.com",
                password = encodedPassword,
                name = "Admin",
                role = Role.ADMIN,
                department = null
            )

            userRepository.save(admin)
        }
    }
}