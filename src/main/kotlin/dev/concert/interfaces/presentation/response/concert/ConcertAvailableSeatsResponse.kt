package dev.concert.interfaces.presentation.response.concert

import dev.concert.application.concert.dto.ConcertSeatsDto

data class ConcertAvailableSeatsResponse (
    val concertOptionId : Long,
    val seats : List<ConcertSeatsDto>
)