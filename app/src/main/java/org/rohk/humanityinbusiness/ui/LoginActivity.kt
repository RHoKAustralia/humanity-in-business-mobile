package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.http.model.RequestLoginModel
import org.rohk.humanityinbusiness.http.model.ResponseModel
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail.setText(PreferenceUtils().getLoginEmail(this))

        btnSignIn.setOnClickListener {
//            val request = RequestLoginModel(etEmail.text.toString(), etPassword.text.toString())
//
//            ServiceAPI().login(
//                request,
//                object : Callback<ResponseModel> {
//                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
//                    }
//
//                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
//                        Toast.makeText(applicationContext, "Oops, login failed!", Toast.LENGTH_LONG).show()
//                    }
//                })
        }
    }
}
