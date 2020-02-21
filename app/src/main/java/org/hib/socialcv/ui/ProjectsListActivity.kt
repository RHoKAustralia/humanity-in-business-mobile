package org.hib.socialcv.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_projects.*
import kotlinx.android.synthetic.main.activity_projects_dashboard.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.hib.socialcv.R
import org.hib.socialcv.http.ServiceAPI
import org.hib.socialcv.http.model.RequestJoinTeamModel
import org.hib.socialcv.ui.viewmodel.ProfileModel
import org.hib.socialcv.ui.viewmodel.TeamModel
import org.hib.socialcv.utils.GlideApp
import org.hib.socialcv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProjectsListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var listAdapter: ProjectAdapter
    lateinit var teamsList: List<TeamModel>
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        setTitle("Projects")

        nav_view.setNavigationItemSelectedListener(this)

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        listAdapter = ProjectAdapter(this, ::selectionListener)

        recycler_view.adapter = listAdapter

        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)

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
        nav_header_textView.text = profileModel.full_name
        nav_header_points.text = "${profileModel.contributed_hours} Hours"
        val url = PreferenceUtils().getSelectedAvatar(this)
        if (!url.isNullOrBlank()) {
            GlideApp.with(this)
                .load(url)
                .into(nav_header_imageView)
        }
    }

//    private fun selectionListener(selectedTeam: TeamModel) {
//        val intent = Intent(this, TeamActivity::class.java)
//        intent.putExtra("team_id", selectedTeam.team_Id)
//        intent.putExtra("project_id", selectedTeam.project.id)
//        intent.putExtra("name", selectedTeam.project.name)
//        intent.putExtra("description", selectedTeam.project.description)
//        intent.putExtra("owner", selectedTeam.project.owner)
//        intent.putExtra("image_url", selectedTeam.project.image_url)
//        startActivity(intent)

//    }

    private fun selectionListener(selectedTeam: TeamModel) {
        val request = RequestJoinTeamModel(
            PreferenceUtils().getUserId(this)
        )

        ServiceAPI().joinTeam(
            selectedTeam.team_Id,
            request,
            object : Callback<Unit> {
                override fun onResponse(
                    call: Call<Unit>,
                    response: Response<Unit>
                ) {
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {}
            })
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

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
//            R.id.nav_item_leaderboard -> startActivity(Intent(this, LeaderboardActivity::class.java))
            R.id.nav_item_profile -> startActivity(Intent(this, ProfileActivity::class.java))
            R.id.nav_item_community -> {
                startActivity(Intent(this, CommunitySelectionActivity::class.java))
                finish()
            }
            R.id.nav_item_event -> {
                val communityId = PreferenceUtils().getSelectedCommunityId(this)
                val intent = Intent(this, EventActivity::class.java)
                intent.putExtra("community_id", communityId)
                startActivity(intent)
                finish()
            }

//            R.id.nav_item_avatar -> {
//                startActivity(Intent(this, AvatarSelectionActivity::class.java))
//                finish()
//            }
        }

        // close drawer when item is tapped
        drawer_layout.closeDrawers();

        return true
    }
}