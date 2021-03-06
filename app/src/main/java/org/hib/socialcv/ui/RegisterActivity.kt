package org.hib.socialcv.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.hib.socialcv.http.ServiceAPI
import org.hib.socialcv.http.model.ResponseModel
import org.hib.socialcv.http.model.RequestRegisterModel
import org.hib.socialcv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(org.hib.socialcv.R.layout.activity_register)
        btnRegister.setOnClickListener {
            register()
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
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
                etJobTitle.text.toString(),
                etCompany.text.toString()
            )

            ServiceAPI().register(
                request,
                object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: Call<ResponseModel>,
                        response: Response<ResponseModel>
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
                                    AboutYouActivity::class.java
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

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
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
                etEmail.text.isNullOrBlank())

    private fun hideLoadingAnimation() {
        animationView.visibility = View.GONE
        layoutContainer.visibility = View.VISIBLE
    }
}
