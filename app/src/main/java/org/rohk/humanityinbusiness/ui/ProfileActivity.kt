package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_profile.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.ui.viewmodel.ChallengeModel
import org.rohk.humanityinbusiness.ui.viewmodel.EventModel
import org.rohk.humanityinbusiness.ui.viewmodel.GoalSelectionModel
import org.rohk.humanityinbusiness.ui.viewmodel.ProfileModel
import org.rohk.humanityinbusiness.utils.DateUtils
import org.rohk.humanityinbusiness.utils.GlideApp
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileActivity : AppCompatActivity() {

    var sdgUrlList = mutableListOf<String>()
    lateinit var profileModel: ProfileModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

//        tvCompanyTitle.setOnClickListener {
//            val intent = Intent(this, BusinessProfileActivity::class.java)
//            intent.putExtra("ID", profileModel.company_id)
//            startActivity(intent)
//        }

        getProfile()
        getAllEvents(1)
        //setMySDGs()
        //getUpcomingChallenges()
        //getCompletedChallenges()
    }

    private fun getProfile() {
        ServiceAPI().getProfile(
            PreferenceUtils().getUserId(this),
            object : Callback<ProfileModel> {
                override fun onResponse(
                    call: Call<ProfileModel>,
                    response: Response<ProfileModel>
                ) {
                    response.body()?.let {
                        setProfile(it)
                    } ?: Toast.makeText(applicationContext, "Oops, could not fetch SDGs!", Toast.LENGTH_LONG).show()
                    hideLoadingAnimation()
                }

                override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                    Toast.makeText(applicationContext, "Oops, could not fetch SDGs!", Toast.LENGTH_LONG).show()
                    hideLoadingAnimation()
                }
            })
    }

    private fun getUpcomingChallenges() {
        ServiceAPI().getUpcomingChallengesForUser(
            PreferenceUtils().getUserId(this),
            object : Callback<List<ChallengeModel>> {
                override fun onResponse(
                    call: Call<List<ChallengeModel>>,
                    response: Response<List<ChallengeModel>>
                ) {
                    setUpcomingChallenge(response.body())
                }

                override fun onFailure(call: Call<List<ChallengeModel>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Oops, could not fetch upcoming challenges!", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun setUpcomingChallenge(upcomingChallenges: List<ChallengeModel>?) {
        val challengeModel = upcomingChallenges?.firstOrNull()
        if (challengeModel != null) {
           val challengeDate = DateUtils().getFormattedDate(challengeModel.challenge_date)
            tvUpcomingChallengeTitle.text =
                "${challengeModel.title} ${challengeDate}"
            GlideApp.with(this@ProfileActivity)
                .load(challengeModel.image_url)
                .into(imgUpcomingChallenge)
        } else {
            linUpcomingChallenge.visibility = View.GONE
        }
    }

//    private fun getCompletedChallenges() {
//        ServiceAPI().getCompletedChallenges(
//            PreferenceUtils().getUserId(this),
//            object : Callback<List<ChallengeModel>> {
//                override fun onResponse(
//                    call: Call<List<ChallengeModel>>,
//                    response: Response<List<ChallengeModel>>
//                ) {
//                    response.body()?.let {
//                        var selectedSDGs = mutableListOf<String>()
//                        it.forEach { sdg -> selectedSDGs.add(sdg.title) }
//                        var arrayAdapter = ArrayAdapter(
//                            this@ProfileActivity,
//                            android.R.layout.simple_list_item_1,
//                            selectedSDGs.toTypedArray()
//                        )
//
//                        listPastActivities.adapter = arrayAdapter
//                    } ?: Toast.makeText(
//                        applicationContext,
//                        "Oops, could not fetch completed challenges!",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//
//                override fun onFailure(call: Call<List<ChallengeModel>>, t: Throwable) {
//                    Toast.makeText(applicationContext, "Oops, could not fetch completed challenges!", Toast.LENGTH_LONG)
//                        .show()
//                }
//            })
//    }

    private fun setProfile(profileModel: ProfileModel) {
        this.profileModel = profileModel
        tvName.text = profileModel.full_name
        tvPoints.text = "1412 points"
//        tvPoints.text = "${profileModel.total_points} points"
        tvCompanyTitle.text = profileModel.title

//        GlideApp.with(this)
//            .load(profileModel.image_url)
//            .into(expandedImage)

        expandedImage.setOnClickListener{
            selectAvatar()
        }
    }

        private fun selectionListener(selectedEvent: EventModel) {
        val intent = Intent(this, EventActivity::class.java)
        intent.putExtra("ID", selectedEvent.id)
        intent.putExtra("community_id", selectedEvent.community_id)
        intent.putExtra("name", selectedEvent.name)
        intent.putExtra("description", selectedEvent.description)
        intent.putExtra("date", selectedEvent.date)
        intent.putExtra("hours", selectedEvent.hours)
        intent.putExtra("image_url", selectedEvent.image_url)
        startActivity(intent)

    }

    override fun onResume() {
        super.onResume()
        expandedImage.setImageDrawable(ContextCompat.getDrawable(this, PreferenceUtils().getSelectedAvatar(this)))

    }

    private fun selectAvatar() {
        startActivity(Intent(applicationContext, AvatarSelectionActivity::class.java))
    }

//    private fun setMySDGs() {
//        val selectedSDGs = PreferenceUtils().getSelectedSDGIds(this).split(",").map { it.trim() }
//        if (!selectedSDGs.isNullOrEmpty()) {
//            selectedSDGs.forEach {sdg ->
//                if(!sdg.isNullOrBlank()) {
//                    getSdgById(sdg)
//                } else {
//                    hideMyFights()
//                }
//            }
//            if(!sdgUrlList.isNullOrEmpty()) {
//                setHorizontalImageList(sdgUrlList)
//            } else {
//                hideMyFights()
//            }
//        } else {
//            hideMyFights()
//        }
//    }

//    private fun getSdgById(sdgId: String) {
//        ServiceAPI().getSDGById(
//            sdgId,
//            object : Callback<List<GoalSelectionModel>> {
//                override fun onResponse(
//                    call: Call<List<GoalSelectionModel>>,
//                    response: Response<List<GoalSelectionModel>>
//                ) {
//                    response.body()?.firstOrNull()?.let {
//                        sdgUrlList.add(it.image_url)
//                    } ?: Toast.makeText(applicationContext, "Oops, could not fetch my SDG!", Toast.LENGTH_LONG).show()
//                }
//
//                override fun onFailure(call: Call<List<GoalSelectionModel>>, t: Throwable) {
//                    hideMyFights()
//                    Toast.makeText(applicationContext, "Oops, could not fetch my SDG!", Toast.LENGTH_LONG).show()
//                }
//            })
//    }

//    private fun setHorizontalImageList(items: List<String>) {
//        val adapter = ImageAdapter(this)
//        adapter.setList(items)
//        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
//            this,
//            androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
//            false
//        )
//        list.layoutManager = layoutManager
//        list.adapter = adapter
//
//    }
//
//    private fun hideMyFights() {
//        tvMyFights.visibility = View.GONE
//        list.visibility = View.GONE
//    }

    private fun hideLoadingAnimation() {
        animationView.visibility = View.GONE
        layoutContainer.visibility = View.VISIBLE
    }

        private fun getAllEvents(communityId : Int) {
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
                    Toast.makeText(applicationContext, "Oops, could not fetch events!", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun setUpcomingEvent(upcomingEvents: List<EventModel>?) {
        val eventModel = upcomingEvents?.firstOrNull()
        if (eventModel != null) {
            tvUpcomingChallengeTitle.text = eventModel.name
            GlideApp.with(this@ProfileActivity)
                .load(eventModel.image_url)
                .into(imgUpcomingChallenge)

            linUpcomingChallenge.setOnClickListener{
                selectionListener(eventModel)
            }
        } else {
            linUpcomingChallenge.visibility = View.GONE
        }
    }

}
