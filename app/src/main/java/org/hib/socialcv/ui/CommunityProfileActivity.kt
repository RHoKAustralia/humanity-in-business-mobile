package org.hib.socialcv.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_community_profile.*
import org.hib.socialcv.R
import org.hib.socialcv.http.ServiceAPI
import org.hib.socialcv.ui.viewmodel.CommunityModel
import org.hib.socialcv.utils.GlideApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityProfileActivity : AppCompatActivity() {

    private var communityId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_profile)
        communityId = intent.getIntExtra("ID", 0)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getCommunityProfile()
    }

    private fun getCommunityProfile() {
        ServiceAPI().getCommunityProfile(
            communityId,
            object : Callback<CommunityModel> {
                override fun onResponse(
                    call: Call<CommunityModel>,
                    response: Response<CommunityModel>
                ) {
                    response.body()?.let {
                        tvName.text = it.name
                        tvSubTitle.text = it.description

                        GlideApp.with(this@CommunityProfileActivity)
                            .load(it.image_url)
                            .into(expandedImage)
                    } ?: Toast.makeText(
                        applicationContext,
                        "Oops, could not get community profile!",
                        Toast.LENGTH_LONG
                    ).show()

                    hideLoadingAnimation()
                }

                override fun onFailure(call: Call<CommunityModel>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Oops, could not get community profile!",
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
