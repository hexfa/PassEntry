package com.example.passentry.data

import android.content.SharedPreferences
import android.util.Log
import com.example.passentry.data.remote.login.LoginRequest
import com.example.passentry.data.remote.login.LoginResponse
import com.example.passentry.data.remote.service.PassEntryService
import com.example.passentry.data.remote.tap.TapResponse
import io.reactivex.Single
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(
    private val passEntryService: PassEntryService,
    private val appInfo: SharedPreferences
) :
    LoginRepository {
    override fun login(loginRequest: LoginRequest): Single<LoginResponse> {
        Log.d("login", loginRequest.password)
        return passEntryService.login(loginRequest)
    }

    override fun taps(loginRequest: LoginRequest): Single<List<TapResponse>> {
        return passEntryService.taps()
    }


    private fun onSuccessfulLogin(loginResponse: LoginResponse) {
        Log.d("LoginRepositoryImp", loginResponse.token)
        appInfo.edit().apply {
            putString("AUTH_TOKEN", loginResponse.token)

        }.apply()

    }
}