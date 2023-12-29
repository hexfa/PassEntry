package com.example.passentry.usecase.errors


interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
