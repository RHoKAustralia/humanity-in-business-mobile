package org.hib.socialcv.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_event.*
import org.hib.socialcv.R
import org.hib.socialcv.http.ServiceAPI
import org.hib.socialcv.ui.model.EventModel
import org.hib.socialcv.ui.model.MembersModel
import org.hib.socialcv.ui.model.ProfileModel
import org.hib.socialcv.utils.GlideApp
import org.hib.socialcv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventActivity : AppCompatActivity() {

    lateinit var listAdapter: EventSelectionAdapter

    private var eventsList = listOf<EventModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        initView()
    }

    private fun initView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerViewCommunitySelection.layoutManager = layoutManager
        listAdapter = EventSelectionAdapter(this, ::selectionListener, ::attendeesSelectionListener)
        recyclerViewCommunitySelection.adapter = listAdapter
        getAllEvents(intent.getStringExtra("community_id"))
        getProfile()
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

                        eventsList = it

                        if(eventsList.isNullOrEmpty()) {
                            Toast.makeText(
                                applicationContext,
                                "Oops, could not find any event, please select a different community!",
                                Toast.LENGTH_LONG)
                                .show()
                            PreferenceUtils().setSelectedCommunityId(this@EventActivity, "") // Reset selection
                            startActivity(Intent(this@EventActivity, CommunitySelectionActivity::class.java))
                            finish()
                        } else {

                            listAdapter.setList(eventsList)

                            for (event: EventModel in eventsList) {
                                getAttendees(event.id)
                            }
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

    private fun selectionListener(selectedEvent: EventModel) {
        PreferenceUtils().setSelectedEventId(this, selectedEvent.id.toString())
        startActivity(Intent(applicationContext, ProjectsListActivity::class.java))
        finish()
    }

    private fun attendeesSelectionListener(selectedEvent: EventModel) {
        val intent = Intent(applicationContext, AttendeesActivity::class.java)
        intent.putExtra("event_image", selectedEvent.image_url)
        intent.putExtra("event_id", selectedEvent.id.toString())
        startActivity(intent)
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
        tvProfileName.text = "Hey, ${profileModel.full_name.split(" ")[0]}"

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

    private fun getAttendees(eventId: Int) {
        animationView.visibility = View.VISIBLE
        layoutContainer.visibility = View.GONE
        ServiceAPI().getAtendeesByEventId(
            eventId.toString(),
            object : Callback<List<MembersModel>> {
                override fun onResponse(
                    call: Call<List<MembersModel>>,
                    response: Response<List<MembersModel>>
                ) {
                    animationView.visibility = View.GONE
                    layoutContainer.visibility = View.VISIBLE
                    response.body()?.let { list ->
                        eventsList.find { it.id == eventId }?.attendeesList = list
                        listAdapter.setList(eventsList)
                    } ?: Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch attendees!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onFailure(call: Call<List<MembersModel>>, t: Throwable) {
                    animationView.visibility = View.GONE
                    layoutContainer.visibility = View.VISIBLE
                    Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch attendees!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Please click on the event to select it.", Toast.LENGTH_LONG).show()
    }
}
