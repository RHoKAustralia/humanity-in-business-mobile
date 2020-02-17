package org.hib.socialcv.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about_you.*
import org.hib.socialcv.R
import org.hib.socialcv.http.ServiceAPI
import org.hib.socialcv.http.model.RequestAboutYouModel
import org.hib.socialcv.http.model.ResponseModel
import org.hib.socialcv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutYouActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_you)

        btnSave.setOnClickListener {
            register()
        }

        tvSkip.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    CommunitySelectionActivity::class.java
                )
            )
        }
    }

    private fun register() {

        layoutContainer.visibility = View.GONE
        animationView.visibility = View.VISIBLE

        val request = RequestAboutYouModel(
            etReason.text.toString(),
            etVolunteerDays.text.toString()
        )

        ServiceAPI().aboutYou(
            PreferenceUtils().getUserId(this),
            request,
            object : Callback<ResponseModel> {
                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    startActivity(
                        Intent(
                            applicationContext,
                            CommunitySelectionActivity::class.java
                        )
                    )
                    finish()
                    hideLoadingAnimation()
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Oops, request failed!",
                        Toast.LENGTH_LONG
                    ).show()
                    hideLoadingAnimation()
                }
            })
    }

    private fun hideLoadingAnimation() {
        animationView.visibility = View.GONE
        layoutContainer.visibility = View.VISIBLE
    }
}
