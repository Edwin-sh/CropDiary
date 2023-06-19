package com.example.cropdiary.ui.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cropdiary.core.SharedPrefUserHelper
import com.example.cropdiary.data.auth.ProviderType
import com.example.cropdiary.databinding.ActivityMainBinding
import com.example.cropdiary.ui.view.Auth.AuthActivity
import com.example.cropdiary.ui.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityMainBinding
    private lateinit var email: String
    private lateinit var provider: String
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Setup
        setup()
    }

    private fun setup() {
        val prefs = SharedPrefUserHelper.getUserPrefs(this)
        this.email = prefs.email.toString()
        this.provider = prefs.provider.toString()
        binding.textViewEmail.text = email
        binding.textViewProvider.text = provider
        binding.button.setOnClickListener {
            signOut()
        }
        authViewModel.authResultModel.observe(this) {
            SharedPrefUserHelper.clearPrefs(this)
            if (it.isSuccess)
                startActivity(Intent(this, AuthActivity::class.java))
        }
    }

    private fun signOut() {
        authViewModel.signOut(
            ProviderType.valueOf(provider),
            googleSignInClient
        )
    }
}