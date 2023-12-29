package com.example.passentry.usecase

import com.example.passentry.data.LoginRepository
import com.example.passentry.data.remote.login.LoginRequest
import com.example.passentry.data.remote.login.LoginResponse
import io.reactivex.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) :
    SingleUseCase<LoginResponse>() {
    private var loginRequest: LoginRequest? = null


    fun saveLogin(loginRequest: LoginRequest) {
        this.loginRequest = loginRequest
    }

    override fun buildUseCaseSingle(): Single<LoginResponse> {
        return repository.login(loginRequest!!)

    }


}