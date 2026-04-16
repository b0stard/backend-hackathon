package com.example.demo.service

import com.example.demo.entity.User
import com.example.demo.enums.Role
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthService(
    private val userService: UserService,
    private val redisService: RedisService
) {

    fun login(email: String, password: String): Pair<User, String> {
        val user = userService.findByEmail(email)
            ?: throw RuntimeException("User not found")

        val encodedPassword = user.password
            ?: throw RuntimeException("User password is null")

        if (!userService.checkPassword(password, encodedPassword)) {
            throw RuntimeException("Wrong password")
        }

       val sessionId = UUID.randomUUID().toString()
      //  redisService.saveSession(sessionId, user.id)

        return Pair(user, sessionId)
    }

    fun logout(sessionId: String?) {
        if (sessionId != null) {
            redisService.deleteSession(sessionId)
        }
    }

    fun getCurrentUser(request: HttpServletRequest): User {
        val sessionId = request.cookies
            ?.find { it.name == "sessionId" }
            ?.value
            ?: throw RuntimeException("Not authorized")

        val userId = redisService.getUserId(sessionId)
            ?: throw RuntimeException("Session expired")

        return userService.getUserById(userId)
            ?: throw RuntimeException("User not found")
    }

    fun requireAdmin(request: HttpServletRequest): User {
        val user = getCurrentUser(request)

        if (user.role != Role.ADMIN) {
            throw RuntimeException("Forbidden")
        }

        return user
    }
}