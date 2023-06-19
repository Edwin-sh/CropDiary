package com.example.cropdiary.core

import android.util.Log
import com.example.cropdiary.R
import com.google.firebase.auth.FirebaseAuthException

object ExceptionsHelper {
    fun authException(exception: Exception): Exception {
        try {
            throw exception
        } catch (e: FirebaseAuthException) {
            val errorMessage = when (e.errorCode) {
                "ERROR_USER_NOT_FOUND" ->
                    R.string.there_is_no_user_with_this_email.toString()
                "ERROR_EMAIL_ALREADY_IN_USE" -> R.string.email_already_in_use.toString()
                "ERROR_WRONG_PASSWORD" -> R.string.wrong_password.toString()
                else -> {
                    Log.w("Tag", "errorCode: ${e.errorCode}")
                    "R.string"
                }
            }
            return Exception(errorMessage, exception)
        } catch (e: Exception) {
            return Exception(
                R.string.an_error_ocurred_authenticating_the_user.toString(),
                exception
            )
        }
    }
}