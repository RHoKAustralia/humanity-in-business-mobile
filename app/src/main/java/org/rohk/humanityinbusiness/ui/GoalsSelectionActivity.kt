package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_goals_selection.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.http.model.RequestSDGModel
import org.rohk.humanityinbusiness.http.model.ResponseModel
import org.rohk.humanityinbusiness.ui.viewmodel.GoalSelectionModel
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class GoalsSelectionActivity : AppCompatActivity() {

    lateinit var sdgList: List<GoalSelectionModel>
    lateinit var listAdapter: GoalsSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals_selection)
        initView()

        btnDone.setOnClickListener {
            sendSelectedGoals()
        }
    }

    private fun initView() {
        recyclerViewGoalsSelection.layoutManager = GridLayoutManager(this, 3)
        recyclerViewGoalsSelection.addItemDecoration(GridItemDecoration(10, 3))

        listAdapter = GoalsSelectionAdapter(this, ::selectionListener)
        recyclerViewGoalsSelection.adapter = listAdapter
        getSDGGoals()
    }

    private fun selectionListener(goalSelectionModel: GoalSelectionModel) {
      sdgList.forEach {
          if(it.id == goalSelectionModel.id) {
              it.isSelected = goalSelectionModel.isSelected
          }
      }
    }

    private fun getSDGGoals() {
        ServiceAPI().getAllSDGGoals(
            object : Callback<List<GoalSelectionModel>> {
                override fun onResponse(
                    call: Call<List<GoalSelectionModel>>,
                    response: Response<List<GoalSelectionModel>>
                ) {
                    response.body()?.let {
                        sdgList = it
                        listAdapter.setList(sdgList)
                    } ?: Toast.makeText(applicationContext, "Oops, could not fetch SDGs!", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<List<GoalSelectionModel>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Oops, could not fetch SDGs!", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun sendSelectedGoals() {
        var selectedId = mutableListOf<String>()
        sdgList.filter { it.isSelected }.forEach { selectedId.add(it.id.toString()) }
        val request = RequestSDGModel(selectedId.toTypedArray())

        PreferenceUtils().setSelectedSDGIds(this, selectedId.joinToString { "," })

        ServiceAPI().sendGoalsSelection(
            PreferenceUtils().getUserId(this),
            request,
            object : Callback<ResponseModel> {
                override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                    if(response.code() == HttpURLConnection.HTTP_OK) {
                        PreferenceUtils().setGoalsSelectionStatus(this@GoalsSelectionActivity, true)
                        startActivity(Intent(applicationContext, RegisterSuccessActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Oops, selection failed!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Toast.makeText(applicationContext, "Oops, selection failed!", Toast.LENGTH_LONG).show()
                }
            })
    }
}
