package org.hib.socialcv.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.hib.socialcv.R
import org.hib.socialcv.http.ServiceAPI
import org.hib.socialcv.http.model.ResponseModel
import org.hib.socialcv.http.model.RequestLoginModel
import org.hib.socialcv.utils.PreferenceUtils
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
            object : Callback<ResponseModel> {
                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        val preferenceUtils = PreferenceUtils()
                        response.body()?.let {
                            preferenceUtils.setUserId(this@LoginActivity, it.id)
                        }

                        preferenceUtils.setLoginEmail(this@LoginActivity, etEmail.text.toString())

                        if (preferenceUtils.getSelectedCommunityId(this@LoginActivity).isBlank()) {
                            startActivity(Intent(this@LoginActivity, CommunitySelectionActivity::class.java))
                        }
                        // TODO fetch selected ids from profile, not from shared prefs ( issue is if you login in a new place, it tries to select new community)
//                        else  if (preferenceUtils.getSelectedEventId(this@LoginActivity).isBlank()) {
//                            val communityId = PreferenceUtils().getSelectedCommunityId(this@LoginActivity)
//                            val intent = Intent(this@LoginActivity, EventActivity::class.java)
//                            intent.putExtra("community_id", communityId)
//                            startActivity(intent)
//                        }
                        else {
                            startActivity(Intent(applicationContext, ProjectsListActivity::class.java))
                        }
                        finish()
                    } else if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        showDialog("Oops, email or password is incorrect!")
                    } else {
                        showDialog("Oops, login failed!")
                    }
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
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
