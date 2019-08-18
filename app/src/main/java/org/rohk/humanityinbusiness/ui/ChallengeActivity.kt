package org.rohk.humanityinbusiness.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_challenge.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.http.model.RegisterResponseModel
import org.rohk.humanityinbusiness.http.model.RequestAddChallengeModel
import org.rohk.humanityinbusiness.ui.viewmodel.ChallengeModel
import org.rohk.humanityinbusiness.utils.DateUtils
import org.rohk.humanityinbusiness.utils.GlideApp
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class ChallengeActivity : AppCompatActivity() {

    lateinit var challenge: ChallengeModel
    private var challengeId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        challengeId = intent.getIntExtra("ID", 0)

        getChallenge()

        btnJoin.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_joined)

        val yesBtn = dialog.findViewById(R.id.btnYes) as Button
        yesBtn.setOnClickListener {
            addChallenge(dialog)
        }
        dialog.show()
    }

    private fun getChallenge() {
        ServiceAPI().getChallenge(
            challengeId,
            object : Callback<ChallengeModel> {
                override fun onResponse(call: Call<ChallengeModel>, response: Response<ChallengeModel>) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        response.body()?.let {
                            challenge = it
                            setChallenge()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Oops, could not fetch challenge!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ChallengeModel>, t: Throwable) {
                    Toast.makeText(applicationContext, "Oops, could not fetch challenge!", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun setChallenge() {
        tvTitle.text = challenge.title
        tvSubTitle.text = "${challenge.points} points"
        tvDesc.text = challenge.description
        tvLocation.text = challenge.location
        tvDate.text = DateUtils().getFormattedDate(challenge.challenge_date)

        GlideApp.with(this)
            .load(challenge.image_url)
            .into(imgChallenge)

        // todo badges - horizontal listview
    }

    private fun addChallenge(dialog: Dialog) {
        val request = RequestAddChallengeModel(
            challengeId,
            PreferenceUtils().getUserId(this)
        )

        ServiceAPI().addChallenge(
            request,
            object : Callback<RegisterResponseModel> {
                override fun onResponse(call: Call<RegisterResponseModel>, response: Response<RegisterResponseModel>) {
                    dialog.dismiss()
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Oops, could not join challenge!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                    Toast.makeText(applicationContext, "Oops, could not join challenge!", Toast.LENGTH_LONG).show()
                }
            })
    }
}
