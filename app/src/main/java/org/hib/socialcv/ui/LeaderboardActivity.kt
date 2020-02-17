package org.hib.socialcv.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_leaderboard.*
import org.hib.socialcv.R
import org.hib.socialcv.http.ServiceAPI
import org.hib.socialcv.ui.viewmodel.UserLeaderBoardModel
import org.hib.socialcv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LeaderboardActivity : AppCompatActivity() {

    lateinit var communityList: List<UserLeaderBoardModel>
    lateinit var listAdapter: LeaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("user_id", leaderBoardModel.id.toString())
        startActivity(intent)
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
