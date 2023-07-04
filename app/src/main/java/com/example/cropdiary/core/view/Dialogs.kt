package com.example.cropdiary.core.view

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.cropdiary.R

object Dialogs {
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
        val activity=context as Activity
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(activity.getString(R.string.succes))
        builder.setMessage(message)
        builder.setPositiveButton(R.string.accept) { _, _ -> metodo(activity) }
        val dialog = builder.create()
        dialog.show()
    }

    fun showSuccesAlert(context: Context, message: String) {
        val activity=context as Activity
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(activity.getString(R.string.succes))
        builder.setMessage(message)
        builder.setPositiveButton(R.string.accept, null)
        val dialog = builder.create()
        dialog.show()
    }

    fun showPrivacyTermsDialog(activity: Activity) {
        //Creating values
        val builder = AlertDialog.Builder(activity)

        val view = activity.layoutInflater.inflate(R.layout.dialog_privacy_policy, null)
        //Moving values to builder
        builder.setView(view)
        //Creating dialog
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btn = view.findViewById<Button>(R.id.btnCloseDialog)
        btn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}