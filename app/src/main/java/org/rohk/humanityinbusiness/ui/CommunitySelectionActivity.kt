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
    }

    private fun initView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerViewCommunitySelection.layoutManager = layoutManager
        listAdapter = CommunitySelectionAdapter(this, ::selectionListener)
        recyclerViewCommunitySelection.adapter = listAdapter
        getAllCommunities()
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

    private fun selectionListener(communitySelectionModel: CommunityModel) {
        PreferenceUtils().setSelectedCommunityId(this, communitySelectionModel.id)
        layoutContainer.visibility = View.GONE
        animationView.visibility = View.VISIBLE

        val intent = Intent(this, EventActivity::class.java)
        intent.putExtra("community_id", communitySelectionModel.id)
        startActivity(intent)
        finish()

    }
}
