package org.hib.socialcv.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_image_list.view.*
import org.hib.socialcv.R
import org.hib.socialcv.utils.GlideApp

class ImageAdapter(mainContext: Context, val layoutResId: Int) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private val context = mainContext

    private var list = listOf<String>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(layoutResId, viewGroup, false)
        return ViewHolder(v)
    }

    fun setList(listChallenges: List<String>) {
        this.list = listChallenges
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(vh: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val viewHolder = vh as ViewHolder
        viewHolder.bindView(list[position], context)
    }

    class ViewHolder(
        itemView: View
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bindView(url: String, context: Context) {
            GlideApp.with(context)
                .load(url)
                .into(itemView.imgItem)
        }

    }

}