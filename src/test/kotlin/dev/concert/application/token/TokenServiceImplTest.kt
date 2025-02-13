package dev.concert.application.token

import dev.concert.application.token.dto.TokenValidationResult
import dev.concert.domain.repository.TokenRepository
import dev.concert.domain.entity.QueueTokenEntity
import dev.concert.domain.entity.UserEntity
import dev.concert.domain.entity.status.QueueTokenStatus
import dev.concert.domain.service.token.TokenServiceImpl
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class TokenServiceImplTest {

    @Mock
    lateinit var tokenRepository: TokenRepository

    @InjectMocks
    lateinit var tokenServiceImpl: TokenServiceImpl

    @Test
    fun `특정 유저에 대해서 토큰이 생성 되어야 한다`() {
        // given
        val user = UserEntity(name = "test")

        // when
        val token = tokenServiceImpl.generateToken(user)

        // then
        assertNotNull(token)
    }

    @Test
    fun `토큰이 만료되지 않았을 때 true를 반환해야 한다`() {
        // given
        val user = UserEntity(name = "test")
        val token = "test"
        val queueTokenEntity = QueueTokenEntity(token = token, user = user)

        given(tokenRepository.findByToken(token)).willReturn(queueTokenEntity)

        // when
        val tokenResult = tokenServiceImpl.validateToken(token)

        // then
        assertThat(tokenResult).isNotEqualTo(TokenValidationResult.VALID)
    }

    @Test
    fun `토큰이 주어지면 해당 토큰에 대한 정보를 반환해야 한다`() {
        // given
        val user = UserEntity(name = "test")
        val token = "test"
        val queueTokenEntity = QueueTokenEntity(token = token, user = user)

        given(tokenRepository.findByUser(user)).willReturn(queueTokenEntity)

        // when
        val tokenResponseDto = tokenServiceImpl.getToken(user)

        // then
        assertNotNull(tokenResponseDto)
        assertThat(tokenResponseDto.token).isEqualTo(token)
    }

    @Test
    fun `토큰이 WAITING 상태인지 확인한다`() {
        // given
        val user = UserEntity(name = "test")
        val token = "test"
        val queueTokenEntity = QueueTokenEntity(token = token, user = user)

        given(tokenRepository.findByToken(token)).willReturn(queueTokenEntity)

        // when
        val tokenResult = tokenServiceImpl.validateToken(token)

        // then
        assertThat(tokenResult).isEqualTo(TokenValidationResult.NOT_AVAILABLE)
    }
 
    @Test  
    fun `토큰 상태가 WAITING 이면 ACTIVE 로 바꿔준다`() { 
        val user = UserEntity(name = "test") 
        val token = "test" 
        val queueTokenEntity = QueueTokenEntity(token = token, user = user) 
 
        `when`(tokenRepository.findWaitingAndActiveTokens()).thenReturn(listOf(queueTokenEntity)) 
 
        tokenServiceImpl.manageTokenStatus() 
 
        assertThat(queueTokenEntity.status).isEqualTo(QueueTokenStatus.ACTIVE) 
    }  
}
