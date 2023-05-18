package com.example.cropdiary.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cropdiary.core.SharedPrefUserHelper
import com.example.cropdiary.data.model.prefs.UserPrefsModel
import com.example.cropdiary.databinding.ActivitySplashIniBinding
import com.example.cropdiary.ui.view.Auth.AuthActivity
import com.example.cropdiary.ui.view.main.MainActivity
import com.example.cropdiary.ui.view.user.RegistreUserActivity

class SplashIniActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashIniBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashIniBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SharedPrefUserHelper.clearPrefs(this)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        sesion()
        return super.onCreateView(name, context, attrs)
    }

    private fun sesion() {
        object : CountDownTimer(2500, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //
            }

            override fun onFinish() {
                checkUserData()
            }

        }.start();
    }

    private fun checkUserData() {
        val userPrefs: UserPrefsModel = SharedPrefUserHelper.getUserPrefs(this)
        if (userPrefs.email != null && userPrefs.provider != null) {
            if (userPrefs.isRegistered) {
                startActivity(
                    Intent(this@SplashIniActivity, MainActivity::class.java)
                )
            } else {
                finish()

                startActivity(
                    Intent(this@SplashIniActivity, RegistreUserActivity::class.java)
                )
            }
            finish()
        } else {
            startActivity(
                Intent(this@SplashIniActivity, AuthActivity::class.java)
            )
            finish()
        }
    }
}