package org.rohk.humanityinbusiness.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.http.model.RegisterResponseModel
import org.rohk.humanityinbusiness.http.model.RequestLoginModel
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail.setText(PreferenceUtils().getLoginEmail(this))

        btnSignIn.setOnClickListener {
            if (!validateFields()) {
                login()
            } else {
                showDialog("Please enter all details!")
            }
        }
    }

    private fun login() {
        val request = RequestLoginModel(etEmail.text.toString(), etPassword.text.toString())

        ServiceAPI().login(
            request,
            object : Callback<RegisterResponseModel> {
                override fun onResponse(
                    call: Call<RegisterResponseModel>,
                    response: Response<RegisterResponseModel>
                ) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        response.body()?.let {
                            PreferenceUtils().setUserId(this@LoginActivity, it.id)
                        }

                        PreferenceUtils().setLoginEmail(this@LoginActivity, etEmail.text.toString())

                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    } else if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        showDialog("Oops, email or password is incorrect!")
                    } else {
                        showDialog("Oops, login failed!")
                    }
                }

                override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                    showDialog("Oops, login failed!")
                }
            })
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle(message)
            .setNeutralButton("OK") { _, _ -> }
            .create()
            .show()
    }

    private fun validateFields(): Boolean =
        (etPassword.text.isNullOrBlank() ||
                etEmail.text.isNullOrBlank())
}
