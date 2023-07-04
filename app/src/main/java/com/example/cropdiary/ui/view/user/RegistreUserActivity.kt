package com.example.cropdiary.ui.view.user

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cropdiary.R
import com.example.cropdiary.core.SharedPrefUserHelper
import com.example.cropdiary.core.util.Utilities
import com.example.cropdiary.core.view.Dialogs
import com.example.cropdiary.core.view.NavigationAuthHelper
import com.example.cropdiary.core.view.ViewHelper
import com.example.cropdiary.data.auth.ProviderType
import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.databinding.ActivityCreateUserBinding
import com.example.cropdiary.ui.viewmodel.AuthViewModel
import com.example.cropdiary.ui.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
@Singleton
class RegistreUserActivity : AppCompatActivity() {
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityCreateUserBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var email: String
    private lateinit var provider: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }


    private fun setup() {
        val userPrefs = SharedPrefUserHelper.getUserPrefs(this)
        email = userPrefs.email.toString()
        provider = userPrefs.provider.toString()

        with(binding) {
            ViewHelper.setVisibility(progressBarRegistreUser, false)
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
                Dialogs.showPrivacyTermsDialog(this@RegistreUserActivity)
            }

            buttonSave.setOnClickListener { save() }
        }
        authViewModel.authResultModel.observe(this) {
            if (it.isSuccess) {
                SharedPrefUserHelper.clearPrefs(this)
                NavigationAuthHelper.showAuthActivity(this)
            }
        }
        userViewModel._progressbar.observe(this) {
            ViewHelper.setVisibility(binding.progressBarRegistreUser, it)
        }
        userViewModel.userResultModel.observe(this) {
            if (it.getOrNull() == true) {
                Dialogs.showSuccesAlert(
                    this,
                    "Registration successful, you are now part of the CropDiary family",
                    ::okButton
                )
            }
        }
    }

    private fun save() {
        val data = getData()
        if (data != null) {
            userViewModel.createUser(data)
        }
    }

    private fun getData(): UserModel? {
        var userModel: UserModel? = null
        with(binding) {
            val list = mutableListOf<Pair<EditText, String>>()
            list.add(
                Pair(
                    editTextTextPersonName,
                    getString(R.string.you_must_enter_your_names)
                )
            )
            list.add(
                Pair(
                    editTextTextPersonLastName,
                    getString(R.string.you_must_enter_your_last_names)
                )
            )
            list.add(
                Pair(
                    editTextPhone,
                    getString(R.string.you_must_enter_your_phone_number)
                )
            )
            list.add(
                Pair(
                    editTextIdCard,
                    getString(R.string.you_must_enter_your_id_card)
                )
            )
            if (!Utilities.noEmpty(
                    list, this@RegistreUserActivity
                )
            ) {
                return@with
            }
            val listAlpha = mutableListOf<Pair<EditText, String>>()
            listAlpha.add(
                Pair(
                    editTextTextPersonName,
                    getString(R.string.enetr_a_valid_name)
                )
            )
            listAlpha.add(
                Pair(
                    editTextTextPersonLastName,
                    getString(R.string.enter_a_valid_last_name)
                )
            )
            if (!Utilities.isAlpha(listAlpha, this@RegistreUserActivity)) {
                return@with
            }
            userModel = UserModel(
                editTextTextEmailAddress.text.toString(),
                "",
                editTextTextPersonName.text.toString(),
                editTextTextPersonLastName.text.toString(),
                editTextPhone.text.toString(),
                editTextIdCard.text.toString()
            )
        }
        return userModel
    }

    private fun okButton(context: Context) {
        SharedPrefUserHelper.addUserPrefs(this, null, null, true)
        NavigationAuthHelper.showMainOrRegisterActivity(this)
    }
}