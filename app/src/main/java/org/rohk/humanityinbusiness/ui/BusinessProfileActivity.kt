package org.rohk.humanityinbusiness.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_business_profile.*
import kotlinx.android.synthetic.main.activity_business_profile.expandedImage
import kotlinx.android.synthetic.main.activity_business_profile.tvName
import kotlinx.android.synthetic.main.activity_business_profile.tvPoints
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.ui.viewmodel.CompanyModel
import org.rohk.humanityinbusiness.ui.viewmodel.GoalSelectionModel
import org.rohk.humanityinbusiness.ui.viewmodel.LeaderBoardModel
import org.rohk.humanityinbusiness.utils.GlideApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusinessProfileActivity : AppCompatActivity() {

    private var companyId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_profile)
        companyId = intent.getIntExtra("ID", 0)

        getCompanyProfile()
        getLeaderBoard()
        getSDGGoals()
    }

    private fun getCompanyProfile() {
        ServiceAPI().getCompanyProfile(
            companyId.toString(),
            object : Callback<CompanyModel> {
                override fun onResponse(
                    call: Call<CompanyModel>,
                    response: Response<CompanyModel>
                ) {
                    response.body()?.let {
                        tvName.text = it.name
                        // tvSubTitle.text = it.url

                        GlideApp.with(this@BusinessProfileActivity)
                            .load(it.image_url)
                            .into(expandedImage)
                    } ?: Toast.makeText(
                        applicationContext,
                        "Oops, could not get company profile!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onFailure(call: Call<CompanyModel>, t: Throwable) {
                    Toast.makeText(applicationContext, "Oops, could not get company profile!", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun getLeaderBoard() {
        ServiceAPI().getLeaderBoard(
            companyId.toString(),
            object : Callback<List<LeaderBoardModel>> {
                override fun onResponse(
                    call: Call<List<LeaderBoardModel>>,
                    response: Response<List<LeaderBoardModel>>
                ) {
                    response.body()?.let {
                        val leaderBoardModel = it.firstOrNull()
                        leaderBoardModel?.let {
                            tvSubTitle.text = it.title
                            tvPoints.text = "${it.points} points"
                        }

                    } ?: Toast.makeText(
                        applicationContext,
                        "Oops, could not get company leader board!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onFailure(call: Call<List<LeaderBoardModel>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Oops, could not get company leader board!", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }


    private fun getSDGGoals() {
        ServiceAPI().getSDGsForCompany(
            companyId.toString(),
            object : Callback<List<GoalSelectionModel>> {
                override fun onResponse(
                    call: Call<List<GoalSelectionModel>>,
                    response: Response<List<GoalSelectionModel>>
                ) {
                    response.body()?.let {
                        var list = mutableListOf<String>()
                        it.forEach { goal ->
                            list.add(goal.image_url)
                        }

                        if (!list.isNullOrEmpty()) {
                            setHorizontalImageList(list)
                        }

                    } ?: Toast.makeText(applicationContext, "Oops, could not fetch SDGs!", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<List<GoalSelectionModel>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Oops, could not fetch SDGs!", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun setHorizontalImageList(items: List<String>) {
        val adapter = ImageAdapter(this)
        adapter.setList(items)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter

    }
}
