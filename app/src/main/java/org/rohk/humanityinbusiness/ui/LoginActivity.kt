package org.rohk.humanityinbusiness.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun login() {
        layoutContainer.visibility = View.GONE
        animationView.visibility = View.VISIBLE

        val request = RequestLoginModel(etEmail.text.toString(), etPassword.text.toString())

        ServiceAPI().login(
            request,
            object : Callback<RegisterResponseModel> {
                override fun onResponse(
                    call: Call<RegisterResponseModel>,
                    response: Response<RegisterResponseModel>
                ) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        val preferenceUtils = PreferenceUtils()
                        response.body()?.let {
                            preferenceUtils.setUserId(this@LoginActivity, it.id)
                        }

                        preferenceUtils.setLoginEmail(this@LoginActivity, etEmail.text.toString())

                        if (preferenceUtils.getSelectedCommunityId(this@LoginActivity).isBlank()) {
                            startActivity(Intent(this@LoginActivity, CommunitySelectionActivity::class.java))
                        } else  if (preferenceUtils.getSelectedEventId(this@LoginActivity).isBlank()) {
                            startActivity(Intent(this@LoginActivity, EventActivity::class.java))
                        } else {
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                        }
                        finish()
                    } else if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        showDialog("Oops, email or password is incorrect!")
                    } else {
                        showDialog("Oops, login failed!")
                    }
                }

                override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                    hideLoadingAnimation()
                    showDialog("Oops, login failed!")
                }
            })
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle(message)
            .setNeutralButton("OK") { _, _ ->
                hideLoadingAnimation()
            }
            .create()
            .show()
    }

    private fun validateFields(): Boolean =
        (etPassword.text.isNullOrBlank() ||
                etEmail.text.isNullOrBlank())

    private fun hideLoadingAnimation() {
        animationView.visibility = View.GONE
        layoutContainer.visibility = View.VISIBLE
    }
}
