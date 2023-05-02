package com.example.cropdiary.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cropdiary.R
import com.example.cropdiary.controller.UserController
import com.example.cropdiary.view.Auth.AuthActivity
import com.example.cropdiary.databinding.ActivityMainBinding
import com.example.cropdiary.util.ProviderType
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var email: String
    private lateinit var provider: String
    private lateinit var userController: UserController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userController = UserController(this)
        //Setup
        val bundle = intent.extras
        email = bundle?.getString("email").toString()
        provider = bundle?.getString("provider").toString()
        setup(email ?: "", provider ?: "")
        Log.i("Main email: ", email!!)
        Log.i("Main provider: ", provider!!)
        //Save user auth info
        val prefs =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()
    }

    private fun setup(email: String, provider: String) {
        binding.textViewEmail.text = email
        binding.textViewProvider.text = provider
        onClickbtnlogOutListener()

    }

    private fun onClickbtnlogOutListener() {
        binding.button.setOnClickListener {
            userController.signOut(ProviderType.valueOf(provider))
        }
    }
}