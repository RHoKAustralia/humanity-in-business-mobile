package org.rohk.humanityinbusiness.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_goal_selection.view.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.ui.viewmodel.GoalSelectionModel
import org.rohk.humanityinbusiness.utils.GlideApp

class GoalsSelectionAdapter(mainContext: Context, private val clickListener: (GoalSelectionModel) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context = mainContext

    private var list = listOf<GoalSelectionModel>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return GoalsSelectionHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_goal_selection,
                parent,
                false
            ), clickListener
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = viewHolder as GoalsSelectionHolder
        viewHolder.bindView(list[position], context)
    }

    fun setList(listGoals: List<GoalSelectionModel>) {
        this.list = listGoals
        notifyDataSetChanged()
    }

    class GoalsSelectionHolder(itemView: View,
                               private val clickListener: (GoalSelectionModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bindView(goalSelectionModel: GoalSelectionModel, context: Context) {
            itemView.tvGoalTitle.text = goalSelectionModel.title
            GlideApp.with(context)
                .load(goalSelectionModel.image_url)
                .into(itemView.imgGoal)

            itemView.setOnClickListener {
                if(!goalSelectionModel.isSelected) {
                    goalSelectionModel.isSelected = true
                    itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_bright))
                } else {
                    goalSelectionModel.isSelected = false
                    itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
                }

                clickListener(goalSelectionModel)
            }
        }

    }
}