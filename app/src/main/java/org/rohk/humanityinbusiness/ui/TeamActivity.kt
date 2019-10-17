package org.rohk.humanityinbusiness.ui

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_team.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.http.model.RegisterResponseModel
import org.rohk.humanityinbusiness.http.model.RequestJoinTeamModel
import org.rohk.humanityinbusiness.utils.GlideApp
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamActivity : AppCompatActivity() {

    private var teamId: Int = 0
    private var projectId: Int = 0
    private var name: String = ""
    private var description: String = ""
    private var owner: String? = ""
    private var image_url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        teamId = intent.getIntExtra("team_id", 0)
        projectId = intent.getIntExtra("project_id", 0)
        name = intent.getStringExtra("name")
        description = intent.getStringExtra("description")
        owner = intent.getStringExtra("owner") ?: ""
        image_url = intent.getStringExtra("image_url")

        setTeam()

        btnJoin.setOnClickListener {
            showDialog()
        }
    }

    private fun setTeam() {
        tvTitle.text = name
        tvSubTitle.text = description
        tvDesc.text = owner

        GlideApp.with(this)
            .load(image_url)
            .into(imgChallenge)

        hideLoadingAnimation()
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_joined)

        val yesBtn = dialog.findViewById(R.id.btnYes) as Button
        yesBtn.setOnClickListener {
            joinTeam(dialog)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun joinTeam(dialog: Dialog) {
        val request = RequestJoinTeamModel(
            PreferenceUtils().getUserId(this)
        )

        ServiceAPI().joinTeam(
            teamId,
            request,
            object : Callback<RegisterResponseModel> {
                override fun onResponse(
                    call: Call<RegisterResponseModel>,
                    response: Response<RegisterResponseModel>
                ) {
                    dialog.dismiss()
                    finish()
                }

                override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                    finish()
                }
            })
    }

    private fun hideLoadingAnimation() {
        animationView.visibility = View.GONE
        layoutContainer.visibility = View.VISIBLE
    }
}
