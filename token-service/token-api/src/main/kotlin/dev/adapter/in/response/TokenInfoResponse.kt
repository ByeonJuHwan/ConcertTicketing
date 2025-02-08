package dev.adapter.`in`.response

import dev.concert.application.token.dto.TokenResponseDto

data class TokenInfoResponse(
    val token: String,
    val status: String,
    val queueOrder: Int,
    val remainingTime: Long,
){
    companion object {
        fun from(tokenResponseDto: TokenResponseDto): TokenInfoResponse {
            return TokenInfoResponse(
                tokenResponseDto.token,
                tokenResponseDto.status.toString(),
                tokenResponseDto.queueOrder,
                tokenResponseDto.remainingTime
            )
        }
    }
}

