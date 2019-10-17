package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.http.model.RegisterResponseModel
import org.rohk.humanityinbusiness.http.model.RequestRegisterModel
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(org.rohk.humanityinbusiness.R.layout.activity_register) // TODO add image url avatar
        btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        if (!validateFields()) {
            layoutContainer.visibility = View.GONE
            animationView.visibility = View.VISIBLE

            val request = RequestRegisterModel(
                etFullname.text.toString(),
                etPassword.text.toString(),
                etEmail.text.toString(),
                etTitle.text.toString(),
                arrayOf() // skills
            )

            ServiceAPI().register(
                request,
                object : Callback<RegisterResponseModel> {
                    override fun onResponse(
                        call: Call<RegisterResponseModel>,
                        response: Response<RegisterResponseModel>
                    ) {
                        response.body()?.id?.let {
                            PreferenceUtils().setUserId(this@RegisterActivity, it)
                            PreferenceUtils().setLoginEmail(
                                this@RegisterActivity,
                                etEmail.text.toString()
                            )
                            startActivity(
                                Intent(
                                    applicationContext,
                                    RegisterSuccessActivity::class.java
                                )
                            )
                            finish()
                        } ?: Toast.makeText(
                            applicationContext,
                            "Oops, registration failed!",
                            Toast.LENGTH_LONG
                        ).show()

                        hideLoadingAnimation()
                    }

                    override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            "Oops, registration failed!",
                            Toast.LENGTH_LONG
                        ).show()
                        hideLoadingAnimation()
                    }
                })
        } else {
            Toast.makeText(applicationContext, "Please enter all details!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun validateFields(): Boolean =
        (etFullname.text.isNullOrBlank() ||
                etPassword.text.isNullOrBlank() ||
                etEmail.text.isNullOrBlank() ||
                etTitle.text.isNullOrBlank())

    private fun hideLoadingAnimation() {
        animationView.visibility = View.GONE
        layoutContainer.visibility = View.VISIBLE
    }
}
