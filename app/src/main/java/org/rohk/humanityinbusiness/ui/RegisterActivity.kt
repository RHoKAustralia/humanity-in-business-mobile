package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.http.model.RequestRegisterModel
import org.rohk.humanityinbusiness.http.model.ResponseModel
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        PreferenceUtils().setLoginEmail(this, etEmail.text.toString())

//        val request = RequestRegisterModel(
//            etFullname.text.toString(),
//            etPassword.text.toString(),
//            etEmail.text.toString(),
//            "1",
//            etTitle.text.toString()
//        )
//
//        ServiceAPI().register(
//            request,
//            object : Callback<ResponseModel> {
//                override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                    startActivity(Intent(applicationContext, GoalsSelectionActivity::class.java))
                    finish()
//                }
//
//                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
//                    Toast.makeText(applicationContext, "Oops, registration failed!", Toast.LENGTH_LONG).show()
//                }
//            })


    }
}
