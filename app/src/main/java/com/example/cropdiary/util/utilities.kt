package com.example.cropdiary.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cropdiary.R
import org.apache.commons.validator.EmailValidator
import org.apache.commons.validator.GenericValidator

public class Utilities : AppCompatActivity() {
    companion object {

        fun networkConnection(activity: Activity): Boolean { //Internet Access Verification
            val con = activity.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = con.activeNetworkInfo
            return if (networkInfo != null && networkInfo.isConnected) {
                true
            } else {
                showErrorAlert(
                    activity,
                    activity.getString(R.string.you_dont_have_an_internet_connection)
                )
                false
            }
        }

        fun showErrorAlert(activity: Activity, message: String) {
            val builder = AlertDialog.Builder(activity)
            val view=activity.layoutInflater.inflate(R.layout.alert_dialog, null)
            val title=view.findViewById<TextView>(R.id.title_alert_dialog)
            val text=view.findViewById<TextView>(R.id.tv_text_alert_dialog)
            val btn=view.findViewById<TextView>(R.id.btnCloseDialog)
            title.text=activity.getString(R.string.error)
            text.text=message
            btn.text=activity.getString(R.string.accept)
            builder.setView(view)
            val dialog = builder.create()
            btn.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        fun showSuccesAlert(context: Context, message: String, metodo: Function1<Context, Unit>) {
            /*val activity=context as Activity
            val builder = AlertDialog.Builder(activity)
            val view=activity.layoutInflater.inflate(R.layout.alert_dialog, null)
            builder.setView(view)
            builder.setTitle(activity.getString(R.string.succes))
            builder.setMessage(message)
            builder.setPositiveButton(R.string.accept) { _, _ -> metodo(activity) }
            val dialog = builder.create()
            dialog.show()*/
        }

        fun showSuccesAlert(context: Context, message: String) {
            /*val activity=context as Activity
            val builder = AlertDialog.Builder(activity)
            val view=activity.layoutInflater.inflate(R.layout.alert_dialog, null)
            builder.setView(view)
            builder.setTitle(activity.getString(R.string.succes))
            builder.setMessage(message)
            builder.setPositiveButton(R.string.accept, null)
            val dialog = builder.create()
            dialog.show()*/
        }

        fun isValid(email: EditText, activity: Activity): Boolean {
            if (!validateEmail(email.text.toString())) {
                showErrorAlert(activity, activity.getString(R.string.you_must_enter_a_valid_email))
                return false
            }
            return true
        }

        fun isValid(email: EditText, password: EditText, activity: Activity): Boolean {
            if (!validateEmail(email.text.toString())) {
                showErrorAlert(activity, activity.getString(R.string.you_must_enter_a_valid_email))
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
                    showErrorAlert(
                        activity,
                        activity.getString(R.string.password_must_have_at_least_one_digit_and_one_uppercase_letter)
                    )
                    false
                }
            } else {
                showErrorAlert(
                    activity,
                    activity.getString(R.string.the_password_must_be_at_least_8_digits)
                )
                false
            }
        }

        fun noEmpty(list: List<Pair<EditText, String>>, activity: Activity): Boolean {
            for (value in list) {
                if (value.first.text.isEmpty()) {
                    showErrorAlert(activity, value.second)
                    return false
                }
            }
            return true
        }

        fun noEmpty(pair: Pair<EditText, String>, activity: Activity): Boolean {
            return if (pair.first.text.isEmpty()) {
                showErrorAlert(activity, pair.second)
                false
            } else {
                true
            }
        }
    }
}