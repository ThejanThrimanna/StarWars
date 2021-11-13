package com.thejan.starwars.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.thejan.starwars.R
import com.thejan.starwars.databinding.ActivitySplashBinding
import com.thejan.starwars.helper.startActivityRightToLeft
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    private fun initView() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()
        val animation0 = AnimationUtils.loadAnimation(this, R.anim.zoom_out)
        binding.ivLogo.startAnimation(animation0)
        Timer().schedule(1500) {
            openMain()
        }
    }

    private fun openMain() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivityRightToLeft(this, intent)
        finish()
    }
}