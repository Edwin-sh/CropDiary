package com.example.cropdiary.view.Auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cropdiary.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    /*private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var db: FirebaseFirestore*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(binding.fragContainerAuth.id, AuthFragment())
            .commit()
    }
}
