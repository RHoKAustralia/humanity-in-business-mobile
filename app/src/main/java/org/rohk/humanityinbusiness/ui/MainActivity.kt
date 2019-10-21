package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.ui.viewmodel.ProfileModel
import org.rohk.humanityinbusiness.ui.viewmodel.TeamModel
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var listAdapter: DashboardAdapter
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
        expandedImage.setOnClickListener {
            startActivity(Intent(this, LeaderboardActivity::class.java))
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
                    collapsingToolbarLayout.title = "Choose Your Project"
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title =
                        " "//careful there should a space between double quote otherwise it wont work
                    isShow = false
                }
            }
        })

        getProfile()
        getAllTeams(PreferenceUtils().getSelectedEventId(this))
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
                    } ?: Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch profile!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch profile!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun setProfile(profileModel: ProfileModel) {
        tvName.text = profileModel.full_name
        tvPoints.text = "${profileModel.hours} Hours"
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

    private fun getAllTeams(eventId: String) {
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
                    Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch events!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    hideLoadingAnimation()
                }
            })
    }

    private fun hideLoadingAnimation() {
        animationView.visibility = View.GONE
    }
}