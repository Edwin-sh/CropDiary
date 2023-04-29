package com.example.cropdiary.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cropdiary.view.Auth.AuthActivity
import com.example.cropdiary.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setup
        val bundle=intent.extras
        val email=bundle?.getString("email")
        val provider=bundle?.getString("provider")
        setup(email ?:"", provider ?:"")
    }

    private fun setup(email:String, provider:String) {
        binding.textViewEmail.text=email
        binding.textViewProvider.text=provider
        onClickbtnlogOutListener()

    }
    private fun onClickbtnlogOutListener() {
        binding.button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(
                Intent(this, AuthActivity::class.java)
            )
            finish()
        }
    }
}