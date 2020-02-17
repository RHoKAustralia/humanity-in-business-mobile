package org.hib.socialcv.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_profile.*
import org.hib.socialcv.R
import org.hib.socialcv.http.ServiceAPI
import org.hib.socialcv.ui.viewmodel.CommunityModel
import org.hib.socialcv.ui.viewmodel.EventModel
import org.hib.socialcv.ui.viewmodel.ProfileModel
import org.hib.socialcv.utils.GlideApp
import org.hib.socialcv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileActivity : AppCompatActivity() {

    lateinit var profileModel: ProfileModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getProfile()
        val communityId = PreferenceUtils().getSelectedCommunityId(this)
        getAllEvents(communityId)
        getCommunityProfile(communityId.toInt())
    }

    private fun getProfile() {
        var profileId = intent.getStringExtra("user_id")
        if (profileId.isNullOrBlank()) {
            profileId = PreferenceUtils().getUserId(this)
        }
        ServiceAPI().getProfile(
            profileId,
            object : Callback<ProfileModel> {
                override fun onResponse(
                    call: Call<ProfileModel>,
                    response: Response<ProfileModel>
                ) {
                    response.body()?.let {
                        setProfile(it)
                    } ?: Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch Profile!",
                        Toast.LENGTH_LONG
                    ).show()
                    hideLoadingAnimation()
                }

                override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch Profile!",
                        Toast.LENGTH_LONG
                    ).show()
                    hideLoadingAnimation()
                }
            })
    }

    private fun setProfile(profileModel: ProfileModel) {
        this.profileModel = profileModel
        tvName.text = profileModel.full_name
        tvPoints.text = profileModel.hours.toString()
        tvTitle.text = profileModel.title

        if (!profileModel.image_url.isNullOrBlank() && profileModel.image_url.contains(
                "http",
                false
            )
        ) {
            GlideApp.with(this)
                .load(profileModel.image_url)
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .into(fabImage)
        } else {
            fabImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_user))
        }
    }

    private fun hideLoadingAnimation() {
//        animationView.visibility = View.GONE
//        layoutContainer.visibility = View.VISIBLE
    }

    private fun getAllEvents(communityId: String) {
        ServiceAPI().getAllEvents(communityId,
            object : Callback<List<EventModel>> {
                override fun onResponse(
                    call: Call<List<EventModel>>,
                    response: Response<List<EventModel>>
                ) {
                    response.body()?.let {
                        setUpcomingEvent(it)
                    }
                    hideLoadingAnimation()
                }

                override fun onFailure(call: Call<List<EventModel>>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch events!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }

    private fun setUpcomingEvent(upcomingEvents: List<EventModel>?) {
        val eventModel = upcomingEvents?.firstOrNull()
        if (eventModel != null) {
            GlideApp.with(this@ProfileActivity)
                .load(eventModel.image_url)
                .into(imgEvent)
        } else {
            linMyEvents.visibility = View.GONE
        }
    }

    private fun getCommunityProfile(communityId: Int) {
        ServiceAPI().getCommunityProfile(
            communityId,
            object : Callback<CommunityModel> {
                override fun onResponse(
                    call: Call<CommunityModel>,
                    response: Response<CommunityModel>
                ) {
                    response.body()?.let {
                        GlideApp.with(this@ProfileActivity)
                            .load(it.image_url)
                            .into(imgCommunity)
                    }
                }

                override fun onFailure(call: Call<CommunityModel>, t: Throwable) {

                }
            })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
