package com.example.cropdiary.util

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cropdiary.R
import org.apache.commons.validator.*

public class utilities : AppCompatActivity() {
    companion object {

        fun networkConnection(context: Context): Boolean { //Internet Access Verification
            val con = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = con.activeNetworkInfo
            return if (networkInfo != null && networkInfo.isConnected) {
                true
            } else {
                showErrorAlert(
                    context,
                    context.getString(R.string.you_dont_have_an_internet_connection)
                )
                false
            }
        }

        fun showErrorAlert(context: Context, message: String) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.error))
            builder.setMessage(message)
            builder.setPositiveButton(R.string.accept, null)
            val dialog = builder.create()
            dialog.show()
        }

        fun showSuccesAlert(context: Context, message: String, metodo:Function1<Context, Unit>) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.succes))
            builder.setMessage(message)
            builder.setPositiveButton(R.string.accept) { _, _ -> metodo(context) }
            val dialog = builder.create()
            dialog.show()
        }
        fun showSuccesAlert(context: Context, message: String) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.succes))
            builder.setMessage(message)
            builder.setPositiveButton(R.string.accept, null)
            val dialog = builder.create()
            dialog.show()
        }

        fun isValid(email: EditText, context: Context): Boolean {
            if (!validateEmail(email.text.toString())) {
                showErrorAlert(context, context.getString(R.string.you_must_enter_a_valid_email))
                return false
            }
            return true
        }

        fun isValid(email: EditText, password: EditText, context: Context): Boolean {
            if (!validateEmail(email.text.toString())) {
                showErrorAlert(context, context.getString(R.string.you_must_enter_a_valid_email))
                return false
            }
            if (!validatePassword(password.text.toString(), context)) {
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

        private fun validatePassword(password: String, context: Context): Boolean {
            val longitudMinima = 8 // Longitud mínima requerida para la contraseña
            return if (GenericValidator.minLength(password, longitudMinima)) {
                val patron = Regex("^(?=.*[A-Z])(?=.*\\d).+\$") // Patrón de expresión regular
                val pass = "Evmc2003"
                if (patron.matches(pass)) {
                    true
                } else {
                    showErrorAlert(
                        context,
                        context.getString(R.string.password_must_have_at_least_one_digit_and_one_uppercase_letter)
                    )
                    false
                }
            } else {
                showErrorAlert(
                    context,
                    context.getString(R.string.the_password_must_be_at_least_8_digits)
                )
                false
            }
        }

        fun noEmpty(list: List<Pair<EditText, String>>, context: Context): Boolean {
            for (value in list) {
                if (value.first.text.isEmpty()) {
                    showErrorAlert(context, value.second)
                    return false
                }
            }
            return true
        }

        fun noEmpty(pair: Pair<EditText, String>, context: Context): Boolean {
            return if (pair.first.text.isEmpty()) {
                showErrorAlert(context, pair.second)
                false
            } else {
                true
            }
        }
    }
}