package dev.adapter.`in`.response

data class TokenResponse(
    val token : String,
){
    companion object {
        fun of(token: String): TokenResponse {
            return TokenResponse(token)
        }
    }
}
