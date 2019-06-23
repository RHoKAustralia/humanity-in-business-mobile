package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.utils.PreferenceUtils

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val preferenceUtils = PreferenceUtils()
            if (preferenceUtils.getLoginEmail(this).isNullOrBlank()) {
                startActivity(Intent(this, RegisterActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))

            }
            finish()
        }, 2000)


    }
}
