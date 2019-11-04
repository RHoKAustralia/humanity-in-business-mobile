package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_event.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.ui.viewmodel.EventModel
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventActivity : AppCompatActivity() {

    lateinit var listAdapter: EventSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        initView()
    }

    private fun initView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerViewCommunitySelection.layoutManager = layoutManager
        listAdapter = EventSelectionAdapter(this, ::selectionListener)
        recyclerViewCommunitySelection.adapter = listAdapter
        getAllEvents(intent.getStringExtra("community_id"))
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
                        listAdapter.setList(it)
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
//        startActivity(Intent(applicationContext, AvatarSelectionActivity::class.java))
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}
