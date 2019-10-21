package org.rohk.humanityinbusiness.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_event_selection.view.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.ui.viewmodel.EventModel
import org.rohk.humanityinbusiness.utils.DateUtils
import org.rohk.humanityinbusiness.utils.GlideApp

class EventSelectionAdapter(mainContext: Context, private val clickListener: (EventModel) -> Unit) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private val context = mainContext

    private var list = listOf<EventModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return EventSelectionHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_event_selection,
                parent,
                false
            ), clickListener
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(
        viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
        position: Int
    ) {
        val vh = viewHolder as EventSelectionHolder
        vh.bindView(list[position], context, position)
    }

    fun setList(listEvent: List<EventModel>) {
        this.list = listEvent
        notifyDataSetChanged()
    }

    class EventSelectionHolder(
        itemView: View,
        private val clickListener: (EventModel) -> Unit
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bindView(eventModel: EventModel, context: Context, positon: Int) {
            itemView.tvTitle.text = eventModel.name
            itemView.tvSubTitle.text = "${eventModel.hours} hours"
            itemView.tvDesc.text = eventModel.description
            itemView.tvDate.text = DateUtils().getFormattedDate(eventModel.date)
            GlideApp.with(context)
                .load(eventModel.image_url)
                .into(itemView.imgEvent)
            itemView.setOnClickListener {
                clickListener(eventModel)
            }
        }
    }


}