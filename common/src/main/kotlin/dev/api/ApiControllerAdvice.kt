package dev.api

import dev.error.ConcertException
import dev.error.ErrorCode
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

    @ExceptionHandler(ConcertException::class)
    fun handleCustomException(e: ConcertException): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode
        logger.error("[${errorCode.name}] [code : ${errorCode.status}] [message : ${e.message}]")
        return ResponseEntity(
            ErrorResponse(errorCode.code, e.message ?: errorCode.message),
            errorCode.status
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.error("[IllegalArgumentException] [code : ${HttpStatus.BAD_REQUEST}] [message : ${e.message}]")
        return ResponseEntity(
            ErrorResponse("400", e.message ?: "잘못된 요청입니다"),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("[Exception] [code : ${HttpStatus.INTERNAL_SERVER_ERROR}] [message : ${e.message}]")
        return ResponseEntity(
            ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.code, "에러가 발생했습니다"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}