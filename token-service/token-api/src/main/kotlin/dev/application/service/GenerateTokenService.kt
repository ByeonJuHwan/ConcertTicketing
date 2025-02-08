package dev.application.service

import dev.application.port.`in`.GenerateTokenUseCase
import dev.application.port.out.GetUserPort
import org.springframework.stereotype.Service

@Service
class GenerateTokenService (
    private val getUserPort: GetUserPort,
) : GenerateTokenUseCase {

    override fun generateToken(userId: Long): String {
        TODO("Not yet implemented")
    }
}