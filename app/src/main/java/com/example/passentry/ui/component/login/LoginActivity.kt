package com.example.passentry.ui.component.login

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.example.passentry.databinding.LoginActivityBinding
import com.example.passentry.ui.base.BaseActivity
import com.example.passentry.utils.AUTH_TOKEN
import com.example.passentry.utils.PASSWORD
import com.example.passentry.utils.SingleEvent
import com.example.passentry.utils.USERNAME
import com.example.passentry.utils.setupSnackbar
import com.example.passentry.utils.showToast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


const val TAG = "LoginActivity"

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    @Inject
    lateinit var appInfo: SharedPreferences
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: LoginActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.login.setOnClickListener { doLogin() }
        observeSnackBarMessages(loginViewModel.showSnackBar)
    }


    private fun doLogin() {
        loginViewModel.doLogin(
            binding.username.text.trim().toString(),
            binding.password.text.toString()
        )?.observe(this) {

            Log.d(TAG, it.token)
            appInfo.edit().apply {
                putString(AUTH_TOKEN, it.token)
                putString(PASSWORD, binding.password.text.toString())
                putString(USERNAME, binding.username.text.trim().toString())

            }.apply()
        }

    }


    private fun observeSnackBarMessages(event: LiveData<String>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }
}
