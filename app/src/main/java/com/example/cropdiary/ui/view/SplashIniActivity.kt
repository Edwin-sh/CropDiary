package com.example.cropdiary.ui.view

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cropdiary.core.SharedPrefUserHelper
import com.example.cropdiary.core.view.NavigationAuthHelper
import com.example.cropdiary.data.model.UserPrefsModel
import com.example.cropdiary.databinding.ActivitySplashIniBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashIniActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashIniBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashIniBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //SharedPrefUserHelper.clearPrefs(this)
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
            NavigationAuthHelper.showMainOrRegisterActivity(this)
        } else {
            NavigationAuthHelper.showAuthActivity(this)
        }
    }
}