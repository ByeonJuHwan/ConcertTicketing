package dev.concert.application.payment

import dev.concert.application.payment.dto.PaymentDto
import dev.concert.domain.service.point.PointService
import dev.concert.domain.service.user.UserService
import dev.concert.domain.repository.ConcertRepository
import dev.concert.domain.entity.ConcertEntity
import dev.concert.domain.entity.ConcertOptionEntity
import dev.concert.domain.entity.ReservationEntity
import dev.concert.domain.entity.SeatEntity
import dev.concert.domain.entity.UserEntity
import dev.concert.domain.repository.ReservationRepository
import dev.concert.domain.repository.SeatRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional 
@SpringBootTest 
class PaymentIntegrationTest {

    @Autowired
    private lateinit var paymentFacade: PaymentFacade

    @Autowired
    private lateinit var userService : UserService

    @Autowired
    private lateinit var concertRepository: ConcertRepository

    @Autowired
    private lateinit var seatRepository: SeatRepository

    @Autowired
    private lateinit var pointService : PointService

    @Autowired
    private lateinit var reservationRepository: ReservationRepository


    @BeforeEach
    fun setUp() {
        // given
        val user = userService.saveUser(UserEntity("변주환"))
        val concert = concertRepository.saveConcert(
            ConcertEntity(
                concertName = "콘서트1",
                singer = "가수1",
                startDate = "20241201",
                endDate = "20241201",
                reserveStartDate = "20241201",
                reserveEndDate = "20241201",
            )
        )

        val concertOption = concertRepository.saveConcertOption(
            ConcertOptionEntity(
                concert = concert,
                concertDate = "20241201",
                concertTime = "12:00",
                concertVenue = "올림픽체조경기장",
                availableSeats = 100,
            )
        )

        val seat = seatRepository.save(
            SeatEntity(
                concertOption = concertOption,
                price = 10000,
                seatNo = 1,
            )
        )

        val expiresAt = LocalDateTime.now().plusMinutes(5)

        val reservation = ReservationEntity(
            user = user,
            seat = seat,
            expiresAt = expiresAt,
        )

        // 예약 정보를 저장한다
        reservationRepository.saveReservation(reservation)


        // 포인트 저장
        pointService.chargePoints(user, seat.price)

    }

    @Test 
    fun `예약 정보 결제 테스트`() { 
        // when 
        val payment = paymentFacade.pay(PaymentDto(reservationId = 1)) 
 
        // then 
        assertThat(payment).isNotNull 
        assertThat(payment.reservationId).isEqualTo(1) 
        assertThat(payment.seatNo).isEqualTo(1) 
        assertThat(payment.status.toString()).isEqualTo("PAID") 
        assertThat(payment.price).isEqualTo(10000) 
    } 
} 
