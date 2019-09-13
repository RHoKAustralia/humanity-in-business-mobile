package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.utils.PreferenceUtils

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
//                if (!preferenceUtils.isGoalsSelected(this)) {
//                    startActivity(Intent(this, CommunitySelectionActivity::class.java))
//                } else {
                    startActivity(Intent(this, LoginActivity::class.java))

//                }
                finish()
            }, 1000)
    }
}
