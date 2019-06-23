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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoalsSelectionActivity : AppCompatActivity() {

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

        //This will for default android divider
        recyclerViewGoalsSelection.addItemDecoration(GridItemDecoration(10, 3))

        val listAdapter = GoalsSelectionAdapter(this)
        recyclerViewGoalsSelection.adapter = listAdapter
        listAdapter.setList(generateSDGData())
    }

    private fun sendSelectedGoals() {
//        val request = RequestSDGModel(arrayOf("1", "2", "3"))
//        ServiceAPI().sendGoalsSelection(
//            request,
//            object : Callback<ResponseModel> {
//                override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                    startActivity(Intent(applicationContext, RegisterSuccessActivity::class.java))
                    finish()
//                }
//
//                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
//                    Toast.makeText(applicationContext, "Oops, selection failed!", Toast.LENGTH_LONG).show()
//                }
//            })
    }

    private fun generateSDGData(): List<GoalSelectionModel> = listOf(
        GoalSelectionModel("No poverty", R.drawable.sdg1),
        GoalSelectionModel("Zero hunger", R.drawable.sdg2),
        GoalSelectionModel("Good health and wellbeing", R.drawable.sdg3),
        GoalSelectionModel("Quality education", R.drawable.sdg4),
        GoalSelectionModel("Gender equality", R.drawable.sdg5),
        GoalSelectionModel("Clean water and sanitation", R.drawable.sdg6),
        GoalSelectionModel("Affordable and clean energy", R.drawable.sdg7),
        GoalSelectionModel("Decent work and economic growth", R.drawable.sdg8),
        GoalSelectionModel("Industry, innovation and infrastructure", R.drawable.sdg9),
        GoalSelectionModel("Reduced inequalities", R.drawable.sdg10),
        GoalSelectionModel("Sustainable cities and communities", R.drawable.sdg11),
        GoalSelectionModel("Responsible consumption and production", R.drawable.sdg12),
        GoalSelectionModel("Climate action", R.drawable.sdg13),
        GoalSelectionModel("Life below water", R.drawable.sdg14),
        GoalSelectionModel("Life on land", R.drawable.sdg15),
        GoalSelectionModel("Peace, justice and strong institutions", R.drawable.sdg16),
        GoalSelectionModel("Partnerships for the goals", R.drawable.sdg17)
    )
}
