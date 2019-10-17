package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_event.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.ui.viewmodel.EventModel
import org.rohk.humanityinbusiness.utils.DateUtils
import org.rohk.humanityinbusiness.utils.GlideApp

class EventActivity : AppCompatActivity() {

    lateinit var event: EventModel
    private var eventId: Int = 0
    private var communityId: Int = 0
    private var name: String = ""
    private var description: String = ""
    private var date: String = ""
    private var hours: Int = 0
    private var image_url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        eventId = intent.getIntExtra("ID", 0)
        communityId = intent.getIntExtra("community_id", 0)
        name = intent.getStringExtra("name")
        description = intent.getStringExtra("description")
        date = intent.getStringExtra("date")
        hours = intent.getIntExtra("hours", 0)
        image_url = intent.getStringExtra("image_url")

        event = EventModel(eventId, communityId, name, hours, description, date, image_url)
        setEvent()
    }

    private fun setEvent() {
        tvTitle.text = event.name
        tvSubTitle.text = "${event.hours} hours"
        tvDesc.text = event.description
        tvDate.text = DateUtils().getFormattedDate(event.date)

        GlideApp.with(this)
            .load(event.image_url)
            .into(imgChallenge)

        hideLoadingAnimation()

        btnNext.setOnClickListener {
            selectAvatar()
        }

        // todo connect to community
    }

    private fun selectAvatar() {
        startActivity(Intent(applicationContext, AvatarSelectionActivity::class.java))
        finish()
    }

    private fun hideLoadingAnimation() {
        animationView.visibility = View.GONE
        layoutContainer.visibility = View.VISIBLE
    }
}
