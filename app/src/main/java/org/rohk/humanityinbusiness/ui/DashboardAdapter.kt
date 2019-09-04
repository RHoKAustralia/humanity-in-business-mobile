package org.rohk.humanityinbusiness.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_layout.view.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.ui.viewmodel.TeamModel
import org.rohk.humanityinbusiness.utils.DateUtils
import org.rohk.humanityinbusiness.utils.GlideApp

class DashboardAdapter(mainContext: Context, private val clickListener: (TeamModel) -> Unit) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private val context = mainContext

    private var list = listOf<TeamModel>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v, clickListener)
    }

    fun setList(listChallenges: List<TeamModel>) {
        this.list = listChallenges
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val viewHolder = viewHolder as ViewHolder
        viewHolder.bindView(list[position], context)
    }

    class ViewHolder(
        itemView: View,
        private val clickListener: (TeamModel) -> Unit
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bindView(team: TeamModel, context: Context) {
//            itemView.tvItemTitle.text = event.name
//            itemView.tvItemDate.text = DateUtils().getFormattedDate(event.date)
//            itemView.tvItemPoints.text = "${event.hours} hours"
            // TODO null checks

            itemView.tvItemTitle.text = team.project.name
            itemView.tvItemDate.text = team.project.owner
            itemView.tvItemPoints.text = team.project.description

            GlideApp.with(context)
                .load(team.project.image_url)
                .into(itemView.imgItem)

            itemView.setOnClickListener {
                clickListener(team)
            }
        }

    }

}