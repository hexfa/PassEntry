package com.example.passentry.data.remote.tap

import com.google.gson.annotations.SerializedName

data class TapResponse(
    @SerializedName("tappedAt")
    val tappedAt: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("readerId")
    val readerId: String,
)