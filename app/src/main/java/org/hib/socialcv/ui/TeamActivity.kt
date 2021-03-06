package org.hib.socialcv.ui

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_team.*
import org.hib.socialcv.R
import org.hib.socialcv.http.ServiceAPI
import org.hib.socialcv.http.model.ResponseModel
import org.hib.socialcv.http.model.RequestJoinTeamModel
import org.hib.socialcv.ui.model.ProjectModel
import org.hib.socialcv.ui.model.TeamModel
import org.hib.socialcv.utils.GlideApp
import org.hib.socialcv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamActivity : AppCompatActivity() {

//    private var teamId: Int = 0
    private var projectId: Int = 0
//    private var name: String = ""
//    private var description: String = ""
//    private var owner: String? = ""
//    private var image_url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        teamId = intent.getIntExtra("team_id", 0)
        projectId = intent.getIntExtra("project_id", 0)
        getTeam(projectId.toString())
//        name = intent.getStringExtra("name")
//        description = intent.getStringExtra("description") ?: ""
//        owner = intent.getStringExtra("owner") ?: ""
//        image_url = intent.getStringExtra("image_url")
//        setTeam()
//        btnJoin.setOnClickListener {
//            showDialog()
//        }
    }

    private fun getTeam(teamId: String) {
        ServiceAPI().getTeamById(teamId,
            object : Callback<TeamModel> {
                override fun onResponse(
                    call: Call<TeamModel>,
                    response: Response<TeamModel>
                ) {
                    response.body()?.let {
                       setTeam(it.project)
                    }
                    hideLoadingAnimation()
                }

                override fun onFailure(call: Call<TeamModel>, t: Throwable) {
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


    private fun setTeam(teamModel: ProjectModel) {
        tvTitle.text = teamModel.name
        tvSubTitle.text = teamModel.description
        tvDesc.text = teamModel.owner

        GlideApp.with(this)
            .load(teamModel.image_url)
            .into(imgChallenge)

        hideLoadingAnimation()
    }

//    private fun showDialog() {
//        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.dialog_joined)
//
//        val yesBtn = dialog.findViewById(R.id.btnYes) as Button
//        yesBtn.setOnClickListener {
//            joinTeam(dialog)
//            dialog.dismiss()
//        }
//        dialog.show()
//    }
//
//    private fun joinTeam(dialog: Dialog) {
//        val request = RequestJoinTeamModel(
//            PreferenceUtils().getUserId(this)
//        )
//
////        ServiceAPI().joinTeam(
////            teamId,
////            request,
////            object : Callback<ResponseModel> {
////                override fun onResponse(
////                    call: Call<ResponseModel>,
////                    response: Response<ResponseModel>
////                ) {
////                    dialog.dismiss()
////                    finish()
////                }
////
////                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
////                    finish()
////                }
////            })
//    }

    private fun hideLoadingAnimation() {
        animationView.visibility = View.GONE
        layoutContainer.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
