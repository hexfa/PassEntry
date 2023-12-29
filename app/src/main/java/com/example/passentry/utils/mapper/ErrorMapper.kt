package com.example.passentry.utils.mapper

import android.content.Context
import com.example.passentry.R
import com.example.passentry.utils.CHECK_YOUR_FIELDS
import com.example.passentry.utils.NETWORK_ERROR
import com.example.passentry.utils.NO_INTERNET_CONNECTION
import com.example.passentry.utils.PASS_WORD_ERROR
import com.example.passentry.utils.SEARCH_ERROR
import com.example.passentry.utils.USER_NAME_ERROR
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorMapper @Inject constructor(@ApplicationContext val context: Context) :
    ErrorMapperSource {

    override fun getErrorString(errorId: Int): String {
        return context.getString(errorId)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
            Pair(NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet)),
            Pair(NETWORK_ERROR, getErrorString(R.string.network_error)),
            Pair(PASS_WORD_ERROR, getErrorString(R.string.invalid_password)),
            Pair(USER_NAME_ERROR, getErrorString(R.string.invalid_username)),
            Pair(CHECK_YOUR_FIELDS, getErrorString(R.string.invalid_username_and_password)),
            Pair(SEARCH_ERROR, getErrorString(R.string.search_error))
        ).withDefault { getErrorString(R.string.network_error) }
}
