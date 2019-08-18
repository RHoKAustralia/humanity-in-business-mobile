package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.expandedImage
import kotlinx.android.synthetic.main.activity_main.tvName
import kotlinx.android.synthetic.main.activity_main.tvPoints
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.ui.viewmodel.ChallengeModel
import org.rohk.humanityinbusiness.ui.viewmodel.ProfileModel
import org.rohk.humanityinbusiness.utils.GlideApp
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var listAdapter: DashboardAdapter
    lateinit var challengesList: List<ChallengeModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
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
                    collapsingToolbarLayout.title = "Let's start a challenge"
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title =
                        " "//careful there should a space between double quote otherwise it wont work
                    isShow = false
                }
            }
        })

        getProfile()
        getUpcomingChallenges()
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
        tvPoints.text = "${profileModel.total_points} points"
    }


    private fun selectionListener(selectedChallenge: ChallengeModel) {
        val intent = Intent(this, ChallengeActivity::class.java)
        intent.putExtra("ID", selectedChallenge.id)
        startActivity(intent)

    }

    private fun getUpcomingChallenges() {
        ServiceAPI().getUpcomingChallenges(
            object : Callback<List<ChallengeModel>> {
                override fun onResponse(
                    call: Call<List<ChallengeModel>>,
                    response: Response<List<ChallengeModel>>
                ) {
                    response.body()?.let {
                        challengesList = it
                        listAdapter.setList(challengesList)
                    }
                }

                override fun onFailure(call: Call<List<ChallengeModel>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Oops, could not fetch challenges!", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }
}