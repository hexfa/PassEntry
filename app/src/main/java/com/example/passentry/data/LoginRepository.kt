package com.example.passentry.data

import com.example.passentry.data.remote.login.LoginRequest
import com.example.passentry.data.remote.login.LoginResponse
import com.example.passentry.data.remote.tap.TapList
import com.example.passentry.data.remote.tap.TapResponse
import io.reactivex.Completable
import io.reactivex.Single

interface LoginRepository {
    fun login(loginRequest: LoginRequest): Single<LoginResponse>
    fun taps(loginRequest: LoginRequest): Single<List<TapResponse>>


}