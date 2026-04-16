package com.example.demo.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisService(
    private val redisTemplate: StringRedisTemplate
) {

    fun saveSession(sessionId: String, userId: Long) {
        redisTemplate.opsForValue()
            .set(sessionId, userId.toString(), 1, TimeUnit.DAYS)
    }

    fun getUserId(sessionId: String): Long? {
        return redisTemplate.opsForValue()
            .get(sessionId)
            ?.toLongOrNull()
    }

    fun deleteSession(sessionId: String) {
        redisTemplate.delete(sessionId)
    }
}