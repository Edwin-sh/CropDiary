package com.example.cropdiary.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cropdiary.view.Auth.AuthActivity
import com.example.cropdiary.databinding.ActivitySplashIniBinding
import java.util.Timer
import java.util.TimerTask

class SplashIniActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashIniBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashIniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Temporizador para iniciar una nueva pantalla
        val timerTask = object : TimerTask() {
            override fun run() {
                startActivity(
                    Intent(this@SplashIniActivity, AuthActivity::class.java)
                )
                finish()
            }
        }

        val timer =  Timer()
        timer.schedule(timerTask, 2500)
    }
}