package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.expandedImage
import kotlinx.android.synthetic.main.activity_main.tvName
import kotlinx.android.synthetic.main.activity_main.tvPoints
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.ui.viewmodel.EventModel
import org.rohk.humanityinbusiness.ui.viewmodel.ProfileModel
import org.rohk.humanityinbusiness.ui.viewmodel.TeamModel
import org.rohk.humanityinbusiness.utils.GlideApp
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var listAdapter: DashboardAdapter
//    lateinit var challengesList: List<ChallengeModel>
//    lateinit var eventsList: List<EventModel>
    lateinit var teamsList: List<TeamModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        listAdapter = DashboardAdapter(this, ::selectionListener)

        recycler_view.adapter = listAdapter

        val mToolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(mToolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        val collapsingToolbarLayout = findViewById(R.id.toolbar_layout) as CollapsingToolbarLayout
        val appBarLayout = findViewById(R.id.app_bar) as AppBarLayout
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = true
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title = "Let's start something new"
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title =
                        " "//careful there should a space between double quote otherwise it wont work
                    isShow = false
                }
            }
        })

        getProfile()
        getAllTeams(1) // todo select event
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
                }

                override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                    Toast.makeText(applicationContext, "Oops, could not fetch SDGs!", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun setProfile(profileModel: ProfileModel) {
        tvName.text = profileModel.full_name
        tvPoints.text = "${profileModel.total_points} hours"
    }

    private fun selectionListener(selectedTeam: TeamModel) {
        val intent = Intent(this, TeamActivity::class.java)
        intent.putExtra("team_id", selectedTeam.team_Id)
        intent.putExtra("project_id", selectedTeam.project.id)
        intent.putExtra("name", selectedTeam.project.name)
        intent.putExtra("description", selectedTeam.project.description)
        intent.putExtra("owner", selectedTeam.project.owner)
        intent.putExtra("image_url", selectedTeam.project.image_url)
        startActivity(intent)

    }


//    private fun selectionListener(selectedEvent: EventModel) {
//        val intent = Intent(this, EventActivity::class.java)
//        intent.putExtra("ID", selectedEvent.id)
//        intent.putExtra("community_id", selectedEvent.community_id)
//        intent.putExtra("name", selectedEvent.name)
//        intent.putExtra("description", selectedEvent.description)
//        intent.putExtra("date", selectedEvent.date)
//        intent.putExtra("hours", selectedEvent.hours)
//        intent.putExtra("image_url", selectedEvent.image_url)
//        startActivity(intent)
//
//    }

//    private fun getUpcomingChallenges() {
//        ServiceAPI().getUpcomingChallenges(
//            object : Callback<List<ChallengeModel>> {
//                override fun onResponse(
//                    call: Call<List<ChallengeModel>>,
//                    response: Response<List<ChallengeModel>>
//                ) {
//                    response.body()?.let {
//                        challengesList = it
//                        listAdapter.setList(challengesList)
//                    }
//                    hideLoadingAnimation()
//                }
//
//                override fun onFailure(call: Call<List<ChallengeModel>>, t: Throwable) {
//                    Toast.makeText(applicationContext, "Oops, could not fetch challenges!", Toast.LENGTH_LONG)
//                        .show()
//                    hideLoadingAnimation()
//                }
//            })
//    }

    private fun getAllTeams(eventId : Int) {
        ServiceAPI().getTeamsByEventId(eventId,
            object : Callback<List<TeamModel>> {
                override fun onResponse(
                    call: Call<List<TeamModel>>,
                    response: Response<List<TeamModel>>
                ) {
                    response.body()?.let {
                        teamsList = it
                        listAdapter.setList(teamsList)
                    }
                    hideLoadingAnimation()
                }

                override fun onFailure(call: Call<List<TeamModel>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Oops, could not fetch events!", Toast.LENGTH_LONG)
                        .show()
                    hideLoadingAnimation()
                }
            })
    }
//    private fun getAllEvents(communityId : Int) {
//        ServiceAPI().getAllEvents(communityId,
//            object : Callback<List<EventModel>> {
//                override fun onResponse(
//                    call: Call<List<EventModel>>,
//                    response: Response<List<EventModel>>
//                ) {
//                    response.body()?.let {
//                        eventsList = it
//                        listAdapter.setList(eventsList)
//                    }
//                    hideLoadingAnimation()
//                }
//
//                override fun onFailure(call: Call<List<EventModel>>, t: Throwable) {
//                    Toast.makeText(applicationContext, "Oops, could not fetch events!", Toast.LENGTH_LONG)
//                        .show()
//                    hideLoadingAnimation()
//                }
//            })
//    }

    private fun hideLoadingAnimation() {
        animationView.visibility = View.GONE
    }
}