package dev.application.port.out

import dev.User

interface GetUserPort {
    fun getUserById(userId: Long): User
}