package org.hib.socialcv.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_attendees.view.*
import org.hib.socialcv.R
import org.hib.socialcv.ui.viewmodel.MembersModel
import org.hib.socialcv.utils.GlideApp

class AttendeesAdapter(mainContext: Context, private val clickListener: (MembersModel) -> Unit) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private val context = mainContext

    private var list = listOf<MembersModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return AttendeesHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_attendees,
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
        val vh = viewHolder as AttendeesHolder
        vh.bindView(list[position], context)
    }

    fun setList(listLeaderboard: List<MembersModel>) {
        this.list = listLeaderboard
        notifyDataSetChanged()
    }

    class AttendeesHolder(
        itemView: View,
        private val clickListener: (MembersModel) -> Unit
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bindView(model: MembersModel, context: Context) {
            itemView.tvName.text = model.full_name
            itemView.tvTitle.text = model.title
            itemView.tvCompany.text = model.company_name
            GlideApp.with(context)
                .load(model.image_url)
                .error(R.drawable.ic_user)
                .placeholder(R.drawable.ic_user)
                .into(itemView.imgAvatar)

            itemView.setOnClickListener {
                clickListener(model)
            }
        }

    }
}