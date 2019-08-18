package org.rohk.humanityinbusiness.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_layout.view.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.ui.viewmodel.ChallengeModel
import org.rohk.humanityinbusiness.utils.DateUtils
import org.rohk.humanityinbusiness.utils.GlideApp

class DashboardAdapter(mainContext: Context, private val clickListener: (ChallengeModel) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context = mainContext

    private var list = listOf<ChallengeModel>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v, clickListener)
    }

    fun setList(listChallenges: List<ChallengeModel>) {
        this.list = listChallenges
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = viewHolder as ViewHolder
        viewHolder.bindView(list[position], context)
    }

    class ViewHolder(
        itemView: View,
        private val clickListener: (ChallengeModel) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        fun bindView(challenge: ChallengeModel, context: Context) {
            itemView.tvItemTitle.text = challenge.title
            itemView.tvItemDate.text = DateUtils().getFormattedDate(challenge.challenge_date)
            itemView.tvItemPoints.text = "${challenge.points} pts"

            GlideApp.with(context)
                .load(challenge.image_url)
                .into(itemView.imgItem)

            itemView.setOnClickListener {
                clickListener(challenge)
            }
        }

    }

}