package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register_success.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.ui.viewmodel.EventModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_success)
        btnLetsgo.setOnClickListener {
            startActivity(Intent(applicationContext, CommunitySelectionActivity::class.java))
            finish()
        }
    }
}
