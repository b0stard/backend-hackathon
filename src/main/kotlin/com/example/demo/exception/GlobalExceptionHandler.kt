package com.example.demo.exception

import com.example.demo.dto.response.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(
        ex: NotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            status = HttpStatus.NOT_FOUND,
            error = "Not Found",
            message = ex.message ?: "Ресурс не найден",
            path = request.requestURI
        )
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(
        ex: BadRequestException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            status = HttpStatus.BAD_REQUEST,
            error = "Bad Request",
            message = ex.message ?: "Некорректный запрос",
            path = request.requestURI
        )
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized(
        ex: UnauthorizedException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            status = HttpStatus.UNAUTHORIZED,
            error = "Unauthorized",
            message = ex.message ?: "Требуется авторизация",
            path = request.requestURI
        )
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbidden(
        ex: ForbiddenException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            status = HttpStatus.FORBIDDEN,
            error = "Forbidden",
            message = ex.message ?: "Доступ запрещён",
            path = request.requestURI
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val message = ex.bindingResult.allErrors
            .mapNotNull { error ->
                when (error) {
                    is FieldError -> "${error.field}: ${error.defaultMessage}"
                    else -> error.defaultMessage
                }
            }
            .joinToString("; ")

        return buildResponse(
            status = HttpStatus.BAD_REQUEST,
            error = "Validation Error",
            message = if (message.isNotBlank()) message else "Ошибка валидации",
            path = request.requestURI
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(
        ex: ConstraintViolationException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            status = HttpStatus.BAD_REQUEST,
            error = "Validation Error",
            message = ex.message ?: "Ошибка ограничения данных",
            path = request.requestURI
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            error = "Internal Server Error",
            message = ex.message ?: "Внутренняя ошибка сервера",
            path = request.requestURI
        )
    }

    private fun buildResponse(
        status: HttpStatus,
        error: String,
        message: String,
        path: String
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(status).body(
            ErrorResponse(
                status = status.value(),
                error = error,
                message = message,
                path = path
            )
        )
    }
}