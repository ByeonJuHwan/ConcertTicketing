package dev.concert.application.concert

import dev.concert.application.concert.dto.ConcertDatesDto
import dev.concert.application.concert.dto.ConcertReservationDto
import dev.concert.application.concert.dto.ConcertReservationResponseDto
import dev.concert.application.concert.dto.ConcertSeatsDto
import dev.concert.application.concert.dto.ConcertsDto
import dev.concert.domain.event.reservation.ReservationSuccessEvent
import dev.concert.domain.event.reservation.publisher.ReservationEventPublisher
import dev.concert.domain.service.concert.ConcertService
import dev.concert.domain.service.reservation.ReservationService
import dev.concert.domain.service.user.UserService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ConcertFacade(
    private val userService: UserService,
    private val concertService: ConcertService,
    private val reservationService: ReservationService,
    @Qualifier("application") private val eventPublisher: ReservationEventPublisher,
){
    fun getConcerts(): List<ConcertsDto> {
        return concertService.getConcerts().map { ConcertsDto(
            id = it.id,
            concertName = it.concertName,
            singer = it.singer,
            startDate = it.startDate,
            endDate = it.endDate,
            reserveStartDate = it.reserveStartDate,
            reserveEndDate = it.reserveEndDate,
        ) }
    }

    fun getAvailableDates(concertId: Long): List<ConcertDatesDto> {
        return concertService.getAvailableDates(concertId).map {
            ConcertDatesDto(
                concertId = it.concert.id,
            concertName = it.concert.concertName,
            availableSeats = it.availableSeats,
            concertTime = it.concertTime,
            concertVenue = it.concertVenue,
            concertDate = it.concertDate,
            )
        }
    }

    fun getAvailableSeats(concertOptionId: Long): List<ConcertSeatsDto> {
        return concertService.getAvailableSeats(concertOptionId).map {
            ConcertSeatsDto(
                seatId = it.id,
                seatNo = it.seatNo,
                price = it.price,
                status = it.seatStatus,
            )
        }
    }

    @Transactional
    fun reserveSeat(request: ConcertReservationDto): ConcertReservationResponseDto {
        // 유저 정보 조회
        val user = userService.getUser(request.userId)

        //예약가능인지 확인하고 좌석 임시배정해 잠근다.
        val reservation = reservationService.createSeatReservation(user, request.seatId)

        // 예약 성공 이벤트 발행
        eventPublisher.publish(ReservationSuccessEvent(reservation.id))

        return ConcertReservationResponseDto(
            status = reservation.status,
            reservationExpireTime = reservation.expiresAt
        )
    }
}