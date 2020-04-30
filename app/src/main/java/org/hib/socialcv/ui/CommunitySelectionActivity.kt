package org.hib.socialcv.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_community_selection.*
import org.hib.socialcv.R
import org.hib.socialcv.http.ServiceAPI
import org.hib.socialcv.ui.model.CommunityModel
import org.hib.socialcv.ui.model.EventModel
import org.hib.socialcv.ui.model.ProfileModel
import org.hib.socialcv.utils.GlideApp
import org.hib.socialcv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunitySelectionActivity : AppCompatActivity() {

    lateinit var communityList: List<CommunityModel>
    lateinit var listAdapter: CommunitySelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_selection)
        initView()
    }

    private fun initView() {
        recyclerViewCommunitySelection.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
        recyclerViewCommunitySelection.addItemDecoration(GridItemDecoration(10, 2))
//        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
//        recyclerViewCommunitySelection.layoutManager = layoutManager
        listAdapter = CommunitySelectionAdapter(this, ::selectionListener)
        recyclerViewCommunitySelection.adapter = listAdapter
        getAllCommunities()
        getProfile()
    }

    private fun getAllCommunities() {
        ServiceAPI().getAllCommunities(
            object : Callback<List<CommunityModel>> {
                override fun onResponse(
                    call: Call<List<CommunityModel>>,
                    response: Response<List<CommunityModel>>
                ) {
                    response.body()?.let {
                        communityList = it
                        listAdapter.setList(communityList)
                    } ?: Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch communities!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onFailure(call: Call<List<CommunityModel>>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch communities!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun selectionListener(communitySelectionModel: CommunityModel) {
        PreferenceUtils().setSelectedCommunityId(this, communitySelectionModel.id)
        layoutContainer.visibility = View.GONE
        animationView.visibility = View.VISIBLE
        getAllEvents(communitySelectionModel.id)
//        val intent = Intent(this, EventActivity::class.java)
//        intent.putExtra("community_id", communitySelectionModel.id)
//        startActivity(intent)
//        finish()

    }

    private fun getAllEvents(communityId: String) {
        ServiceAPI().getAllEvents(communityId,
            object : Callback<List<EventModel>> {
                override fun onResponse(
                    call: Call<List<EventModel>>,
                    response: Response<List<EventModel>>
                ) {
                    response.body()?.let {
                        animationView.visibility = View.GONE
                        layoutContainer.visibility = View.VISIBLE
                        if(it.isNullOrEmpty()) {
                            PreferenceUtils().setSelectedCommunityId(this@CommunitySelectionActivity, "") // Reset selection
                            Toast.makeText(
                                applicationContext,
                                "Oops, no events listed for this community, please select another one!",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        } else {
                            PreferenceUtils().setSelectedEventId(this@CommunitySelectionActivity, it[0].id.toString())
                            startActivity(Intent(applicationContext, ProjectsListActivity::class.java))
                            finish()
                        }
                    }
                }

                override fun onFailure(call: Call<List<EventModel>>, t: Throwable) {
                    animationView.visibility = View.GONE
                    layoutContainer.visibility = View.VISIBLE
                    Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch events!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }


    private fun getProfile() {
        var profileId = intent.getStringExtra("user_id")
        if(profileId.isNullOrBlank()) {
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
                }

                override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch Profile!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun setProfile(profileModel: ProfileModel) {
        tvProfileName.text = "Hey, ${profileModel.full_name?.split(" ")?.get(0)}"

        if (!profileModel.image_url.isNullOrBlank() && profileModel.image_url.contains("http", false)) {
            GlideApp.with(this)
                .load(profileModel.image_url)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_user)
                .into(profileImage)

        } else {
            profileImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_user))
        }
    }
}
