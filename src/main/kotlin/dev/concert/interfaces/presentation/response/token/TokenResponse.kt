package dev.concert.interfaces.presentation.response.token

data class TokenResponse(
    val token : String,
){
    companion object {
        fun of(token: String): TokenResponse {
            return TokenResponse(token)
        }
    }
}
