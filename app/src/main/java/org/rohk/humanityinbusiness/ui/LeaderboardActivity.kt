package org.rohk.humanityinbusiness.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_leaderboard.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.ui.viewmodel.UserLeaderBoardModel
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeaderboardActivity : AppCompatActivity() {

    lateinit var communityList: List<UserLeaderBoardModel>
    lateinit var listAdapter: LeaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
        initView()
    }

    private fun initView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerViewLeaderboard.layoutManager = layoutManager
        listAdapter = LeaderboardAdapter(this, ::selectionListener)
        recyclerViewLeaderboard.adapter = listAdapter
        getLeaderboard()
    }

    private fun selectionListener(leaderBoardModel: UserLeaderBoardModel) {
// TODO load profile
    }

    private fun getLeaderboard() {
        ServiceAPI().getLeaderboard(
            PreferenceUtils().getSelectedCommunityId(this),
            object : Callback<List<UserLeaderBoardModel>> {
                override fun onResponse(
                    call: Call<List<UserLeaderBoardModel>>,
                    response: Response<List<UserLeaderBoardModel>>
                ) {
                    response.body()?.let {
                        communityList = it
                        listAdapter.setList(communityList)
                    } ?: Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch leaderboard!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onFailure(call: Call<List<UserLeaderBoardModel>>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Oops, could not fetch leaderboard!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}
