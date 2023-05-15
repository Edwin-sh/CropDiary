package com.example.cropdiary.view.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cropdiary.R
import com.example.cropdiary.controller.UserController
import com.example.cropdiary.databinding.ActivityCreateUserBinding
import com.example.cropdiary.util.ProviderType

class CreateUserActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCreateUserBinding
    private lateinit var userController: UserController
    private lateinit var email: String
    private lateinit var provider: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userController = UserController(this)
        val bundle = intent.extras
        email = bundle?.getString(getString(R.string.email_pref)).toString()
        provider = bundle?.getString(getString(R.string.provider_pref)).toString()
        binding.buttonCancelaRegistro.setOnClickListener {
            userController.signOut(ProviderType.valueOf(provider))
        }
    }
}