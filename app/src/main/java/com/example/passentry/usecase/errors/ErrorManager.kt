package com.example.passentry.usecase.errors


import com.example.passentry.utils.mapper.ErrorMapper
import javax.inject.Inject

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return Error(errorMapper.errorsMap.getValue(errorCode))
    }
}
