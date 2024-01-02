package com.example.passentry.ui.component.login

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.passentry.data.LoginRepository
import com.example.passentry.data.Resource
import com.example.passentry.data.remote.login.LoginRequest
import com.example.passentry.data.remote.login.LoginResponse
import com.example.passentry.data.remote.tap.TapResponse
import com.example.passentry.ui.base.BaseViewModel
import com.example.passentry.usecase.LoginUseCase
import com.example.passentry.usecase.TapsUseCase
import com.example.passentry.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataRepository: LoginRepository,
    private val loginUseCase: LoginUseCase,
    private val tapsUseCase: TapsUseCase
) : BaseViewModel() {
    private val TAG = "LoginViewModel"

    private val errorLiveData = MutableLiveData<String>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val loginLiveDataPrivate = MutableLiveData<Resource<LoginResponse>>()
    val loginLiveData: LiveData<Resource<LoginResponse>> get() = loginLiveDataPrivate


    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<String>()
    val showSnackBar: LiveData<String> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate


    fun doLogin(userName: String, passWord: String): LiveData<LoginResponse>? {
        val loginLiveData = MutableLiveData<LoginResponse>()
        loginUseCase.saveLogin(LoginRequest(userName, passWord))
        loginUseCase.execute(
            onSuccess = {
                loginLiveData.value = it
                Log.d(TAG, "getMessage: $it")
            },
            onError = {
                Log.e(TAG, "getMessage: ${it.message}")
                errorLiveData.value=it.message
                it.printStackTrace()
            }
        )

        return loginLiveData
    }

    fun error():LiveData<String>{
        //errorLiveData.value=error;
        return errorLiveData;
    }
    fun taps(userName: String, passWord: String): LiveData<List<TapResponse>>? {
        val tapLiveData = MutableLiveData<List<TapResponse>>()
        tapsUseCase.saveTap(LoginRequest(userName, passWord))
        tapsUseCase.execute(
            onSuccess = {
                tapLiveData.value = it
                Log.d(TAG, "getMessage: $it")
            },
            onError = {
                Log.e(TAG, "getMessage: $it")
                showSnackBarPrivate.value = it.message

                it.printStackTrace()
            }
        )

        return tapLiveData
    }


}
