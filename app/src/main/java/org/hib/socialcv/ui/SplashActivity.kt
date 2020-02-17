package org.hib.socialcv.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.hib.socialcv.R
import org.hib.socialcv.utils.PreferenceUtils

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val preferenceUtils = PreferenceUtils()
        if (preferenceUtils.getUserId(this).isNullOrBlank()) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        } else
            Handler().postDelayed({
                    startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 1000)
    }
}
