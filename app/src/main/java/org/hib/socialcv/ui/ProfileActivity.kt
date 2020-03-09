package org.hib.socialcv.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_profile.*
import org.hib.socialcv.R
import org.hib.socialcv.http.ServiceAPI
import org.hib.socialcv.ui.model.CommunityModel
import org.hib.socialcv.ui.model.ImageModel
import org.hib.socialcv.ui.model.ProfileModel
import org.hib.socialcv.ui.model.ProjectModel
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

    private fun hideLoadingAnimation() {
        animationView.visibility = View.GONE
        layoutContainer.visibility = View.VISIBLE
    }

    private fun setProfile(profileModel: ProfileModel) {
        this.profileModel = profileModel
        tvName.text = profileModel.full_name
        tvAboutMe.text = profileModel.why_join_hib
        tvPoints.text = profileModel.contributed_hours.toString()
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

        loadCommunities(profileModel.communities)
        loadProjects(profileModel.projects)
    }

    private fun loadCommunities(list: List<CommunityModel>) {
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCommunities.layoutManager = layoutManager
        val adapter = ImageAdapter(
            this@ProfileActivity, R.layout.item_image_list
        )
        adapter.setList(list.map { ImageModel(it.id, it.name, it.image_url )})
        recyclerViewCommunities.adapter = adapter
    }

    private fun loadProjects(list: List<ProjectModel>) {
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewProjects.layoutManager = layoutManager
        val adapter = ImageAdapter(
            this@ProfileActivity, R.layout.item_image_list_2
        )
        adapter.setList(list.map { ImageModel(it.id.toString(), it.name, it.image_url )})
        recyclerViewProjects.adapter = adapter
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
