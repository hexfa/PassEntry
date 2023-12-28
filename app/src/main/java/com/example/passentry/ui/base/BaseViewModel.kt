package com.example.passentry.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.logging.ErrorManager
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    /*@Inject
    lateinit var errorManager: ErrorManager*/

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}