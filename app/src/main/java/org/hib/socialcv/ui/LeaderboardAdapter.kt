package org.hib.socialcv.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_leaderboard.view.*
import org.hib.socialcv.R
import org.hib.socialcv.ui.model.UserLeaderBoardModel

class LeaderboardAdapter(mainContext: Context, private val clickListener: (UserLeaderBoardModel) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private val context = mainContext

    private var list = listOf<UserLeaderBoardModel>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return LeaderboardHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_leaderboard,
                parent,
                false
            ), clickListener
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val vh = viewHolder as LeaderboardHolder
        vh.bindView(list[position], context)
    }

    fun setList(listLeaderboard: List<UserLeaderBoardModel>) {
        this.list = listLeaderboard
        notifyDataSetChanged()
    }

    class LeaderboardHolder(itemView: View,
                                   private val clickListener: (UserLeaderBoardModel) -> Unit) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bindView(model: UserLeaderBoardModel, context: Context) {
            itemView.tvName.text = model.name
            itemView.tvTitle.text = model.title
            itemView.tvCompany.text = model.company
            itemView.tvHours.text = model.hours.toString()
            itemView.tvProjects.text = model.projects.toString()
            itemView.tvEvents.text = model.events.toString()
//            GlideApp.with(context)
//                .load(model.image_url)
//                .into(itemView.imgAvatar)

            itemView.setOnClickListener {
                clickListener(model)
            }
        }

    }
}