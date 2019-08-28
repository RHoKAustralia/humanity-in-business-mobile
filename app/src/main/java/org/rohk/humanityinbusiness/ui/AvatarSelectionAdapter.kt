package org.rohk.humanityinbusiness.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_goal_selection.view.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.utils.GlideApp

class AvatarSelectionAdapter(mainContext: Context, private val clickListener: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context = mainContext

    private var list = listOf<Int>()

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

    fun setList(listItems: List<Int>) {
        this.list = listItems
        notifyDataSetChanged()
    }

    class GoalsSelectionHolder(
        itemView: View,
        private val clickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        fun bindView(drawableItem: Int, context: Context) {
            itemView.tvGoalTitle.visibility = View.GONE
            GlideApp.with(context)
                .load(drawableItem)
                .into(itemView.imgGoal)

            itemView.setOnClickListener {
                clickListener(drawableItem)
            }
        }

    }
}