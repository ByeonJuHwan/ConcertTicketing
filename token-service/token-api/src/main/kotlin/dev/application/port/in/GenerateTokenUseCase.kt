package dev.application.port.`in`

interface GenerateTokenUseCase {
    fun generateToken (userId: Long): String
}