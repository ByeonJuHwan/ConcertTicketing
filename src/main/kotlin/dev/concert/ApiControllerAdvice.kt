package dev.concert

import dev.concert.exception.UserNotFountException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

data class ErrorResponse(val code: String, val message: String)

@RestControllerAdvice
class ApiControllerAdvice : ResponseEntityExceptionHandler() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse("400", e.message ?: "잘못된 요청입니다"),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(UserNotFountException::class)
    fun handleUserNotFountException(e: UserNotFountException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse("404", e.message ?: "존재하는 회원이 없습니다"),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse("500", "에러가 발생했습니다"),
            HttpStatus.INTERNAL_SERVER_ERROR,
        )
    }
}