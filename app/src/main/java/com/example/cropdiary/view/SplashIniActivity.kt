package com.example.cropdiary.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cropdiary.R
import com.example.cropdiary.view.Auth.AuthActivity
import com.example.cropdiary.databinding.ActivitySplashIniBinding
import com.example.cropdiary.view.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import java.util.Timer
import java.util.TimerTask

class SplashIniActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashIniBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashIniBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sesion()
    }

    private fun sesion() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if (email!=null && provider!=null){
            val timerTask = object : TimerTask() {
                override fun run() {
                    startActivity(
                        Intent(this@SplashIniActivity, MainActivity::class.java)
                    )
                    finish()
                }
            }

            val timer = Timer()
            timer.schedule(timerTask, 2500)
        }else{
            val timerTask = object : TimerTask() {
                override fun run() {
                    startActivity(
                        Intent(this@SplashIniActivity, AuthActivity::class.java).apply {
                            putExtra("email", email)
                            putExtra("provider", provider)
                        }
                    )
                    finish()
                }
            }

            val timer = Timer()
            timer.schedule(timerTask, 2500)
        }
    }
}