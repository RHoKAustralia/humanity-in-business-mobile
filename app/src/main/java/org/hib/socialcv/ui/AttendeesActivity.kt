package org.hib.socialcv.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_attendees.*
import org.hib.socialcv.R
import org.hib.socialcv.http.ServiceAPI
import org.hib.socialcv.ui.viewmodel.MembersModel
import org.hib.socialcv.utils.GlideApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AttendeesActivity : AppCompatActivity() {

    lateinit var attendeesList: List<MembersModel>
    lateinit var listAdapter: AttendeesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendees)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initView()
    }

    private fun initView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerViewAttendees.layoutManager = layoutManager
        listAdapter = AttendeesAdapter(this, ::selectionListener)
        recyclerViewAttendees.adapter = listAdapter

        try {
            GlideApp.with(this@AttendeesActivity)
                .load(intent.getStringExtra("event_image"))
                .into(imgEvent)
        } catch (e: Exception) {
            Log.e("loadEventImage", e.message)
        }

       getAttendees(intent.getStringExtra("event_id"))
    }

    private fun selectionListener(membersModel: MembersModel) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("user_id", membersModel.user_Id.toString())
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun getAttendees(eventId: String) {
        ServiceAPI().getAtendeesByEventId(
            eventId,
            object : Callback<List<MembersModel>> {
                override fun onResponse(
                    call: Call<List<MembersModel>>,
                    response: Response<List<MembersModel>>
                ) {
                    response.body()?.let { list ->
                        attendeesList = list
                        listAdapter.setList(attendeesList)
                    } ?: Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch attendees!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onFailure(call: Call<List<MembersModel>>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch attendees!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}
