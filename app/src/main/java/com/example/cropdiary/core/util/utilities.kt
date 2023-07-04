package com.example.cropdiary.core.util

import android.app.Activity
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.widget.EditText
import com.example.cropdiary.R
import com.example.cropdiary.core.view.Dialogs
import org.apache.commons.validator.EmailValidator
import org.apache.commons.validator.GenericValidator

object Utilities {
    private val patron = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+\$")

    fun networkConnection(activity: Activity): Boolean { //Internet Access Verification
        val con = activity.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = con.activeNetworkInfo
        return if (networkInfo != null && networkInfo.isConnected) {
            true
        } else {
            Dialogs.showErrorAlert(
                activity,
                activity.getString(R.string.you_dont_have_an_internet_connection)
            )
            false
        }
    }

    fun isAlpha(value: Pair<EditText, String>, activity: Activity): Boolean {
        return if (!patron.matches(value.first.text.toString())) {
            Dialogs.showErrorAlert(activity, value.second)
            false
        } else {
            true
        }

    }

    fun isAlpha(list: List<Pair<EditText, String>>, activity: Activity): Boolean {
        for (value in list) {
            if (!patron.matches(value.first.text.toString())) {
                Dialogs.showErrorAlert(activity, value.second)
                return false
            }
        }
        return true
    }

    fun isValid(email: EditText, activity: Activity): Boolean {
        if (!validateEmail(email.text.toString())) {
            Dialogs.showErrorAlert(
                activity,
                activity.getString(R.string.you_must_enter_a_valid_email)
            )
            return false
        }
        return true
    }

    fun isValid(email: EditText, password: EditText, activity: Activity): Boolean {
        if (!validateEmail(email.text.toString())) {
            Dialogs.showErrorAlert(
                activity,
                activity.getString(R.string.you_must_enter_a_valid_email)
            )
            return false
        }
        if (!validatePassword(password.text.toString(), activity)) {
            return false
        }
        return true
    }

    private fun validateEmail(email: String): Boolean {
        val validator = EmailValidator.getInstance()
        if (!validator.isValid(email)) {
            return false
        }
        return true
    }

    private fun validatePassword(password: String, activity: Activity): Boolean {
        val longitudMinima = 8 // Longitud mínima requerida para la contraseña
        return if (GenericValidator.minLength(password, longitudMinima)) {
            val patron = Regex("^(?=.*[A-Z])(?=.*\\d).+\$") // Patrón de expresión regular
            val pass = "Evmc2003"
            if (patron.matches(pass)) {
                true
            } else {
                Dialogs.showErrorAlert(
                    activity,
                    activity.getString(R.string.password_must_have_at_least_one_digit_and_one_uppercase_letter)
                )
                false
            }
        } else {
            Dialogs.showErrorAlert(
                activity,
                activity.getString(R.string.the_password_must_be_at_least_8_digits)
            )
            false
        }
    }

    fun noEmpty(list: List<Pair<EditText, String>>, activity: Activity): Boolean {
        for (value in list) {
            if (value.first.text.isEmpty()) {
                Dialogs.showErrorAlert(activity, value.second)
                return false
            }
        }
        return true
    }

    fun noEmpty(pair: Pair<EditText, String>, activity: Activity): Boolean {
        return if (pair.first.text.isEmpty()) {
            Dialogs.showErrorAlert(activity, pair.second)
            false
        } else {
            true
        }
    }
}