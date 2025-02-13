package dev.concert.interfaces.scheduler.reservation

import dev.concert.domain.service.reservation.ReservationService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SeatReservationScheduler ( 
    private val reservationService: ReservationService
) { 

    /** 
     * 예약 만료 시간을 관리하는 스케줄러 
     */ 
    @Scheduled(fixedRate = 60000) 
    fun seatReservationScheduler() { 
        reservationService.manageReservationStatus() 
    } 
} 
