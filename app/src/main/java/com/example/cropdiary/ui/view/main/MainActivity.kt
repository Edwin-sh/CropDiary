package com.example.cropdiary.ui.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cropdiary.core.FirebaseHelper
import com.example.cropdiary.core.SharedPrefUserHelper
import com.example.cropdiary.data.auth.ProviderType
import com.example.cropdiary.data.repository.UserRepository
import com.example.cropdiary.databinding.ActivityMainBinding
import com.example.cropdiary.ui.view.Auth.AuthActivity
import com.example.cropdiary.ui.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(private val googleSignInClient: GoogleSignInClient): AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var email: String
    private lateinit var provider: String
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Setup

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        authViewModel.authResultModel.observe(this) {
            SharedPrefUserHelper.clearPrefs(this)
            if (it.isSuccess)
                startActivity(Intent(this, AuthActivity::class.java))
        }
        val prefs = SharedPrefUserHelper.getUserPrefs(this)
        email = prefs.email.toString()
        provider = prefs.provider.toString()
        setup(email, provider)
        return super.onCreateView(name, context, attrs)

    }

    private fun setup(email: String, provider: String) {
        binding.textViewEmail.text = email
        binding.textViewProvider.text = provider
        binding.button.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        authViewModel.signOut(
            ProviderType.valueOf(provider),
            googleSignInClient
        )
    }
}