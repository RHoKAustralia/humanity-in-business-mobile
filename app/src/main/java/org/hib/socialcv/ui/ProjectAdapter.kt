package org.hib.socialcv.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_project.view.*
import org.hib.socialcv.R
import org.hib.socialcv.ui.model.TeamModel
import org.hib.socialcv.utils.GlideApp

class ProjectAdapter(mainContext: Context, private val clickListener: (TeamModel) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context = mainContext

    private var list = listOf<TeamModel>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_project, viewGroup, false)
        return ViewHolder(v, clickListener)
    }

    fun setList(listChallenges: List<TeamModel>) {
        this.list = listChallenges
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = vh as ViewHolder
        viewHolder.bindView(list[position], context)
    }

    class ViewHolder(
        itemView: View,
        private val clickListener: (TeamModel) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        fun bindView(team: TeamModel, context: Context) {
            itemView.tvItemTitle.text = team.project.name
//            itemView.tvItemDate.text = team.project.description
            itemView.tvItemPoints.text = team.project.owner // TODO change to hours

            GlideApp.with(context)
                .load(team.project.image_url)
                .into(itemView.imgItem)

            itemView.setOnClickListener {

                setTeam(team, context)

            }
        }

        private fun setTeam(team: TeamModel,context: Context) {

            if(itemView.linDetailsContainer.visibility == View.GONE) {
                itemView.linDetailsContainer.visibility = View.VISIBLE
                itemView.linItemContainer.visibility = View.GONE
                itemView.tvTitle.text = team.project.name
                itemView.tvSubTitle.text = team.project.owner
                itemView.tvDesc.text = team.project.description

                GlideApp.with(context)
                    .load(team.project.image_url)
                    .into(itemView.imgItemDetail)

                itemView.btnJoin.setOnClickListener {
                    itemView.btnJoin.visibility = View.GONE
                    itemView.animationViewJoin.visibility = View.VISIBLE
                    itemView.animationViewJoin.playAnimation()
                    clickListener(team)
                }
            } else {
                itemView.linDetailsContainer.visibility = View.GONE
                itemView.linItemContainer.visibility = View.VISIBLE
            }
        }

    }

}