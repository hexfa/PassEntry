package com.example.passentry

import android.app.Application
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var pref: SharedPreferences

    override fun onCreate() {
        super.onCreate()
    }
}
