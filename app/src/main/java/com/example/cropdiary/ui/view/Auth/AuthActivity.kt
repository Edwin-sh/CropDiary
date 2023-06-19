package com.example.cropdiary.ui.view.Auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cropdiary.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(binding.fragContainerAuth.id, SignInFragment())
            .commit()
    }
}
