package org.hib.socialcv.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.item_event_selection.view.*
import org.hib.socialcv.R
import org.hib.socialcv.http.ServiceAPI
import org.hib.socialcv.ui.viewmodel.EventModel
import org.hib.socialcv.ui.viewmodel.MembersModel
import org.hib.socialcv.utils.DateUtils
import org.hib.socialcv.utils.GlideApp
import org.hib.socialcv.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EventSelectionAdapter(mainContext: Context, private val clickListener: (EventModel) -> Unit, private val attendeesClickListener: (EventModel) -> Unit) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private val context = mainContext

    private var list = listOf<EventModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return EventSelectionHolder(
            context,
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_event_selection,
                parent,
                false
            ), clickListener, attendeesClickListener
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
        private val context: Context,
        itemView: View,
        private val clickListener: (EventModel) -> Unit,
        private val attendeesClickListener: (EventModel) -> Unit
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bindView(eventModel: EventModel, context: Context, position: Int) {
            itemView.tvTitle.text = eventModel.name
            itemView.tvAddress.text = eventModel.location//"${eventModel.hours} hours"
            itemView.tvDateTime.text = DateUtils().getFormattedDate(eventModel.date)
            itemView.tvDate.text = DateUtils().getDayInMonth(eventModel.date)
            itemView.tvDay.text = DateUtils().getMonthInYear(eventModel.date)
            GlideApp.with(context)
                .load(eventModel.image_url)
                .into(itemView.imgEvent)
            itemView.setOnClickListener {
                clickListener(eventModel)
            }
            itemView.imgShare.setOnClickListener {
                shareToSocialMedia(eventModel)
            }
            if(eventModel.attendeesList != null && eventModel.attendeesList.isNotEmpty()) {
                itemView.tvAttendeesCount.visibility = View.VISIBLE
                itemView.tvAttendeesCount.text = "${eventModel.attendeesList.size} going"
                itemView.tvAttendeesCount.setOnClickListener {
                    attendeesClickListener(eventModel)
                }
            } else {
                itemView.tvAttendeesCount.visibility = View.GONE
            }
        }

        fun shareToSocialMedia(eventModel: EventModel) {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.type = "text/plain"
            val shareBody = eventModel.description
            val shareSub = eventModel.name
            myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub)
            myIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            context.startActivity(Intent.createChooser(myIntent, "Share using"))
        }

    }


}