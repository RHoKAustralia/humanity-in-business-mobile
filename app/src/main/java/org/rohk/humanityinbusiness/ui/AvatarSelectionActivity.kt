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
        avatarURL + 0,
        avatarURL + 1,
        avatarURL + 2,
        avatarURL + 3,
        avatarURL + 4,
        avatarURL + 5,
        avatarURL + 6,
        avatarURL + 7,
        avatarURL + 8,
        avatarURL + 9,
        avatarURL + 10,
        avatarURL + 11,
        avatarURL + 12,
        avatarURL + 13,
        avatarURL + 14
    )
}
