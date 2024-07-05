package dev.concert.domain.entity

import dev.concert.domain.entity.status.QueueTokenStatus
import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "concert_option")
class QueueTokenEntity (
    token : String,
    user : UserEntity,
    userUUID : UUID,
    queueOrder : Int,
    remainingTime : Long,
    expiresAt : LocalDateTime,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long = 0

    @Column(nullable = false)
    var token: String = token
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var user : UserEntity = user
        protected set

    var userUUID : UUID = userUUID
        protected set

    @Column(nullable = false)
    var queueOrder: Int = queueOrder
        protected set

    @Column(nullable = false)
    var remainingTime: Long = remainingTime// 잔여 시간 (초 단위)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: QueueTokenStatus = QueueTokenStatus.ACTIVE
}