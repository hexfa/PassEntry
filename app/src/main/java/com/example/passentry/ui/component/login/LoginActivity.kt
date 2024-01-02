package com.example.passentry.ui.component.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.passentry.R
import com.example.passentry.databinding.LoginActivityBinding
import com.example.passentry.ui.base.BaseActivity
import com.example.passentry.ui.component.main.MainActivity
import com.example.passentry.utils.AUTH_TOKEN
import com.example.passentry.utils.PASSWORD
import com.example.passentry.utils.SingleEvent
import com.example.passentry.utils.USERNAME
import com.example.passentry.utils.setupSnackbar
import com.example.passentry.utils.showToast
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

        binding.loginButton.setOnClickListener {
            validateInputs(
                binding.emailEditText,
                binding.passwordEditText,
                binding.emailInputLayout,
                binding.passwordInputLayout,
                binding.errorTxt
            )
        }


        // Define the colors
        val lightColor = ContextCompat.getColor(this, R.color.red_more_light)
        val focusedColor = ContextCompat.getColor(
            this,
            R.color.red_more_light
        ) // If you want a different color when focused

        // Create a ColorStateList
        val colorStateList = android.content.res.ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_focused), // focused
                intArrayOf(-android.R.attr.state_focused)  // unfocused
            ),
            intArrayOf(
                focusedColor, // Color when focused
                lightColor    // Color when not focused
            )
        )
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_blue)

        binding.emailInputLayout.defaultHintTextColor = colorStateList
        binding.passwordInputLayout.defaultHintTextColor = colorStateList


        // Apply the ColorStateList to the TextInputLayout
        binding.emailInputLayout.setBoxStrokeColorStateList(colorStateList)
        binding.passwordInputLayout.setBoxStrokeColorStateList(colorStateList)

        loginViewModel.error().observe(this, Observer {
            binding.errorTxt.visibility = View.VISIBLE
            binding.errorTxt.text = getString(R.string.username_password_is_incorrect)
        })
    }


    private fun doLogin() {
        loginViewModel.doLogin(
            binding.emailEditText.text?.trim().toString(),
            binding.passwordEditText.text.toString()
        )?.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Finish the login activity


            Log.d(TAG, it.token)
            appInfo.edit().apply {
                putString(AUTH_TOKEN, it.token)
                putString(PASSWORD, binding.passwordEditText.text.toString())
                putString(USERNAME, binding.emailEditText.text?.trim().toString())

            }.apply()
        }

    }


    private fun observeSnackBarMessages(event: LiveData<String>, loginButton: MaterialButton) {
        Log.d(TAG, "observeSnackBarMessages: ")
        loginButton.error = event.value;
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }


    private fun validateInputs(
        emailEditText: TextInputEditText,
        passwordEditText: TextInputEditText,
        emailInputLayout: TextInputLayout,
        passwordInputLayout: TextInputLayout,
        errorTextView: TextView
    ) {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Regular expression for validating email
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+" // Simplified regex for example

        var isValid = true

        if (email.isEmpty()) {
            emailInputLayout.error = getString(R.string.email_cannot_empty)
            isValid = false
        } else if (!email.matches(emailPattern.toRegex())) {
            emailInputLayout.error = getString(R.string.invalid_email_format)
            isValid = false
        } else {
            emailInputLayout.error = null
        }

        if (password.isEmpty()) {
            passwordInputLayout.error = getString(R.string.password_cannot_empty)
            isValid = false
        } else if (password.length < 8) {
            passwordInputLayout.error = getString(R.string.password_is_too_short)
            isValid = false
        } else {
            passwordInputLayout.error = null
        }

        if (isValid) {
            errorTextView.visibility = View.GONE
            doLogin()
        }
    }

}
