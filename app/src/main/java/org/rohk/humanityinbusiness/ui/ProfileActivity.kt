package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile.*
import org.rohk.humanityinbusiness.R

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvCompanyTitle.setOnClickListener {
            startActivity(Intent(this, BusinessProfileActivity::class.java))
        }
    }
}
