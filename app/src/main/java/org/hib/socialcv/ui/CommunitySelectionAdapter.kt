package org.hib.socialcv.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_community_selection.view.*
import org.hib.socialcv.R
import org.hib.socialcv.ui.viewmodel.CommunityModel
import org.hib.socialcv.utils.GlideApp

class CommunitySelectionAdapter(
    mainContext: Context,
    private val clickListener: (CommunityModel) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private val context = mainContext

    private var list = listOf<CommunityModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return CommunitySelectionHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_community_selection,
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
        val vh = viewHolder as CommunitySelectionHolder
        vh.bindView(list[position], context)
    }

    fun setList(listCommunity: List<CommunityModel>) {
        this.list = listCommunity
        notifyDataSetChanged()
    }

    class CommunitySelectionHolder(
        itemView: View,
        private val clickListener: (CommunityModel) -> Unit
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bindView(communityModel: CommunityModel, context: Context) {

            itemView.tvCommunityTitle.text = communityModel.name

            GlideApp.with(context)
                .load(communityModel.image_url)
                .into(itemView.imgCommunity)

            itemView.setOnClickListener {
                clickListener(communityModel)
            }
        }
    }


}