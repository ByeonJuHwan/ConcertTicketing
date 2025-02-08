package dev.error

class ConcertException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)