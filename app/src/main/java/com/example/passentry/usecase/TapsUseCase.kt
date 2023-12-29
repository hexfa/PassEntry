package com.example.passentry.usecase

import apk.drivers.main.framework.base.SingleUseCase
import com.example.passentry.data.LoginRepository
import com.example.passentry.data.remote.login.LoginRequest
import com.example.passentry.data.remote.login.LoginResponse
import com.example.passentry.data.remote.tap.TapList
import com.example.passentry.data.remote.tap.TapResponse
import io.reactivex.Single
import javax.inject.Inject

class TapsUseCase @Inject constructor(private val repository: LoginRepository) :
    SingleUseCase<List<TapResponse>>() {
    private var tapRequest : LoginRequest?=null


    fun saveTap(loginRequest: LoginRequest) {
        tapRequest=loginRequest
    }

    override fun buildUseCaseSingle(): Single<List<TapResponse>> {
        return repository.taps(tapRequest!!)

    }


}