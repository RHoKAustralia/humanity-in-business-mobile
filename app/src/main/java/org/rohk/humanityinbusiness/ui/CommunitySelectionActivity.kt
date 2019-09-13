package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_community_selection.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.ui.viewmodel.CommunityModel
import org.rohk.humanityinbusiness.ui.viewmodel.EventModel
import org.rohk.humanityinbusiness.utils.PreferenceUtils
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

        btnDone.visibility = View.GONE

//            setOnClickListener {
//            sendSelectedCommunities()
//        }
    }

    private fun initView() {
//        recyclerViewCommunitySelection.layoutManager =
//            androidx.recyclerview.widget.GridLayoutManager(this, 3)
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerViewCommunitySelection.layoutManager = layoutManager
        listAdapter = CommunitySelectionAdapter(this, ::selectionListener)
        recyclerViewCommunitySelection.adapter = listAdapter
        getAllCommunities()
    }

    private fun selectionListener(communitySelectionModel: CommunityModel) {
        sendSelectedCommunities(communitySelectionModel)

//        communityList.forEach {
//            if (it.id == communitySelectionModel.id) {
//                it.isSelected = communitySelectionModel.isSelected
//            }
//        }
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

    private fun sendSelectedCommunities(communitySelectionModel: CommunityModel) {
        PreferenceUtils().setSelectedCommunityId(this, communitySelectionModel.id)
        getAllEvents(communitySelectionModel.id)

        layoutContainer.visibility = View.GONE
        animationView.visibility = View.VISIBLE
    }

    private fun getAllEvents(communityId: String) {
        ServiceAPI().getAllEvents(communityId,
            object : Callback<List<EventModel>> {
                override fun onResponse(
                    call: Call<List<EventModel>>,
                    response: Response<List<EventModel>>
                ) { // TODO show events list instead of picking the first one
                    response.body()?.let {
                        selectionListener(it[0])
                        animationView.visibility = View.GONE
                        layoutContainer.visibility = View.VISIBLE
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
        val intent = Intent(this, EventActivity::class.java)
        intent.putExtra("ID", selectedEvent.id)
        intent.putExtra("community_id", selectedEvent.community_id)
        intent.putExtra("name", selectedEvent.name)
        intent.putExtra("description", selectedEvent.description)
        intent.putExtra("date", selectedEvent.date)
        intent.putExtra("hours", selectedEvent.hours)
        intent.putExtra("image_url", selectedEvent.image_url)
        startActivity(intent)
        finish()

    }
}
