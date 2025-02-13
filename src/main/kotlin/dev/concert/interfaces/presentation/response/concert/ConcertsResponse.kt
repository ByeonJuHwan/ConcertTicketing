package dev.concert.interfaces.presentation.response.concert

import dev.concert.application.concert.dto.ConcertsDto

data class ConcertsResponse(
    val concerts: List<ConcertsDto>,
)