package com.example.passentry.data.remote.login

import com.google.gson.annotations.SerializedName

import android.os.Parcelable

data class LoginResponse(
    @SerializedName("api-token")
    val token: String
)