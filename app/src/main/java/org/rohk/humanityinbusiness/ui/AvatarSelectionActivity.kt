package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_avatar_selection.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.utils.PreferenceUtils

class AvatarSelectionActivity : AppCompatActivity() {

    companion object  {
       const val avatarURL = "https://humanity-in-business.s3-ap-southeast-2.amazonaws.com/avatars/avatar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar_selection)
        initView()
    }

    private fun initView() {
        recyclerViewAvatarSelection.layoutManager =
            GridLayoutManager(this, 3)

        //This will for default android divider
        recyclerViewAvatarSelection.addItemDecoration(GridItemDecoration(10, 3))

        val listAdapter = AvatarSelectionAdapter(this, ::saveSelectedAvatar)
        recyclerViewAvatarSelection.adapter = listAdapter
        listAdapter.setList(getAvatars())
    }

    private fun saveSelectedAvatar(avatar: String) {
        PreferenceUtils().setSelectedAvatar(this, avatar)
        startActivity(Intent(applicationContext, MainActivity::class.java))

        finish()
    }

    private fun getAvatars(): List<String> = listOf(
        avatarURL + "0.png",
        avatarURL + "1.png",
        avatarURL + "2.png",
        avatarURL + "3.png",
        avatarURL + "4.png",
        avatarURL + "5.png",
        avatarURL + "6.png",
        avatarURL + "7.png",
        avatarURL + "8.png",
        avatarURL + "9.png",
        avatarURL + "10.png",
        avatarURL + "11.png",
        avatarURL + "12.png",
        avatarURL + "13.png",
        avatarURL + "14.png"
    )
}
