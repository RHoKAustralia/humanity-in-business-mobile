package org.rohk.humanityinbusiness.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_avatar_selection.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.utils.PreferenceUtils

class AvatarSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar_selection)
        initView()
    }

    private fun initView() {
        recyclerViewAvatarSelection.layoutManager = GridLayoutManager(this, 3) as RecyclerView.LayoutManager?

        //This will for default android divider
        recyclerViewAvatarSelection.addItemDecoration(GridItemDecoration(10, 3))

        val listAdapter = AvatarSelectionAdapter(this, ::saveSelectedAvatar)
        recyclerViewAvatarSelection.adapter = listAdapter
        listAdapter.setList(getAvatars())
    }

    private fun saveSelectedAvatar(avatar : Int) {
        PreferenceUtils().setSelectedAvatar(this, avatar)
        finish()
    }

    private fun getAvatars(): List<Int> = listOf(
        R.drawable.avatar0,
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar4,
        R.drawable.avatar5,
        R.drawable.avatar6,
        R.drawable.avatar7,
        R.drawable.avatar8,
        R.drawable.avatar9,
        R.drawable.avatar10,
        R.drawable.avatar11,
        R.drawable.avatar12,
        R.drawable.avatar13,
        R.drawable.avatar14
    )
}
