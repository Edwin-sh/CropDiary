package com.example.cropdiary.ui.view.user

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.cropdiary.R
import com.example.cropdiary.core.SharedPrefUserHelper
import com.example.cropdiary.data.auth.ProviderType
import com.example.cropdiary.databinding.ActivityCreateUserBinding
import com.example.cropdiary.databinding.DialogPrivacyPolicyBinding
import com.example.cropdiary.ui.view.Auth.AuthActivity
import com.example.cropdiary.ui.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
@Singleton
class RegistreUserActivity : AppCompatActivity() {
    @Inject lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityCreateUserBinding
    private lateinit var binding12: DialogPrivacyPolicyBinding
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var email: String
    private lateinit var provider: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUserBinding.inflate(layoutInflater)
        binding12 = DialogPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userPrefs = SharedPrefUserHelper.getUserPrefs(this)
        email = userPrefs.email.toString()
        provider = userPrefs.provider.toString()

        with(binding) {
            buttonCancelaRegistro.setOnClickListener {
                authViewModel.signOut(
                    ProviderType.valueOf(provider),
                    googleSignInClient
                )
            }
            buttonSave.isEnabled = false
            editTextTextEmailAddress.text = Editable.Factory.getInstance().newEditable(email)
            checkBoxAccept.setOnClickListener {
                buttonSave.isEnabled = checkBoxAccept.isChecked
            }
            tvMessagePrivacyLink.setOnClickListener {
                showPrivacyTermsDialog()
            }
        }
    }

    private fun showPrivacyTermsDialog() {
        Log.w("Dialogo", "Entró al dialogo")
        //Creating values
        val builder = AlertDialog.Builder(this@RegistreUserActivity)

        val view = layoutInflater.inflate(R.layout.dialog_privacy_policy, null)
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

    override fun onCreateView(
        name: String,
        context: android.content.Context,
        attrs: AttributeSet
    ): View? {
        setup()
        return super.onCreateView(name, context, attrs)
    }

    private fun setup() {

        authViewModel.authResultModel.observe(this, Observer {
            if (it.isSuccess) {
                SharedPrefUserHelper.clearPrefs(this)
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        })
    }
}