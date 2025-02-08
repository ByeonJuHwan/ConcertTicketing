package dev.adapter.`in`.controller

import dev.api.ApiResult
import dev.adapter.`in`.request.TokenRequest
import dev.adapter.`in`.response.TokenInfoResponse
import dev.adapter.`in`.response.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "토큰 발급 API", description = "토큰 발급 API를 제공합니다")
@RestController
@RequestMapping("/queue/tokens")
class TokenController ( 
    private val tokenFacade: TokenFacade,
) { 
 
    @Operation( 
        summary = "토큰 발급 API", 
        description = "유저 아이디를 받아 토큰을 발급합니다", 
    ) 
    @ApiResponses( 
        ApiResponse(responseCode = "200", description = "토큰 발급 성공"),
    )  
    @PostMapping 
    fun createToken( 
        @RequestBody tokenRequest: TokenRequest
    ) : ApiResult<TokenResponse> {
        return ApiResult(TokenResponse.of(tokenFacade.generateToken(tokenRequest.userId)))
    } 
 
    @Operation( 
        summary = "토큰 조회 API", 
        description = "토큰을 조회합니다", 
    ) 
    @ApiResponses( 
        ApiResponse(responseCode = "200", description = "토큰 조회 성공"), 
    ) 
    @GetMapping("/status/{userId}")
    fun getToken( 
        @PathVariable userId: Long,
    ) : ApiResult<TokenInfoResponse> {
        return ApiResult(TokenInfoResponse.from(tokenFacade.getToken(userId)))
    }
}
