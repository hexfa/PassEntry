package com.example.passentry.ui.component.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.example.passentry.ui.base.BaseActivity
import com.example.passentry.ui.component.login.LoginActivity
import com.example.passentry.ui.component.main.MainActivity
import com.example.passentry.utils.AUTH_TOKEN
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    @Inject
    lateinit var appInfo: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (appInfo.getString(AUTH_TOKEN, null) != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


}