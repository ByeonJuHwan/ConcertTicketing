package dev.adapter.`in`.response

import dev.concert.domain.entity.status.QueueTokenStatus

data class QueueTokenStatusResponse(
    val token: String,
    val status: QueueTokenStatus,
    val queueOrder: Int,
    val remainingTime: Long,
)
