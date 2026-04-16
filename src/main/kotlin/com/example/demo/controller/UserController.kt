package com.example.demo.controller

import com.example.demo.entity.User
import com.example.demo.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {


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

        val user = userService.createUser(email, password, name)

        return ResponseEntity.ok(user)
    }
    @GetMapping
    fun getAllUsers(): List<User> {
        return userService.getAllUsers()
    }
}