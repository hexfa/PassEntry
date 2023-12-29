package com.example.passentry.data.remote.service

import com.example.passentry.data.remote.login.LoginRequest
import com.example.passentry.data.remote.login.LoginResponse
import com.example.passentry.data.remote.tap.TapResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PassEntryService {
    @POST("/login")
    fun login(@Body loginRequest: LoginRequest): Single<LoginResponse>

    @GET("/taps")
    fun taps(): Single<List<TapResponse>>
}