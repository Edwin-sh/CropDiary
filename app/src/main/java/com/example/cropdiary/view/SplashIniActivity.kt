package com.example.cropdiary.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.example.cropdiary.R
import com.example.cropdiary.controller.UserController
import com.example.cropdiary.view.Auth.AuthActivity
import com.example.cropdiary.databinding.ActivitySplashIniBinding
import com.example.cropdiary.util.utilities
import com.example.cropdiary.view.main.MainActivity
import com.example.cropdiary.view.user.CreateUserActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import java.util.Timer
import java.util.TimerTask

class SplashIniActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashIniBinding
    private lateinit var userController: UserController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashIniBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userController = UserController(this)
        /*val prefs=getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()*/
        sesion()
    }

    private fun sesion() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString(getString(R.string.email_pref), null)
        val provider = prefs.getString(getString(R.string.provider_pref), null)
        /*object : CountDownTimer(2500, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //
            }

            override fun onFinish() {
                checkUserData()
            }

        }.start();*/

    }

    private fun checkUserData() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString(getString(R.string.email_pref), null)
        val provider = prefs.getString(getString(R.string.provider_pref), null)
        if (email != null && provider != null) {
            if (utilities.networkConnection(this)) {
                userController.getUser(email) { user ->
                    if (user != null) {
                        startActivity(
                            Intent(this@SplashIniActivity, MainActivity::class.java).apply {
                                putExtra("email", email)
                                putExtra("provider", provider)
                            }
                        )
                    } else {
                        startActivity(
                            Intent(this@SplashIniActivity, CreateUserActivity::class.java).apply {
                                putExtra("email", email)
                                putExtra("provider", provider)
                            }
                        )
                    }
                }
                finish()
            }

        } else {
            startActivity(
                Intent(this@SplashIniActivity, AuthActivity::class.java)
            )
            finish()
        }
    }
}