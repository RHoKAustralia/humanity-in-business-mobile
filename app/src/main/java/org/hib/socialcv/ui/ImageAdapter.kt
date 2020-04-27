package org.hib.socialcv.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_image_list.view.*
import org.hib.socialcv.ui.model.ImageModel
import org.hib.socialcv.utils.GlideApp

class ImageAdapter(mainContext: Context, private val layoutResId: Int) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private val context = mainContext

    private var list = listOf<ImageModel>()

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        p1: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(layoutResId, viewGroup, false)
        return ViewHolder(v)
    }

    fun setList(listChallenges: List<ImageModel>) {
        this.list = listChallenges
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(
        vh: androidx.recyclerview.widget.RecyclerView.ViewHolder,
        position: Int
    ) {
        val viewHolder = vh as ViewHolder
        viewHolder.bindView(list[position], context)
    }

    class ViewHolder(
        itemView: View
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bindView(imageModel: ImageModel?, context: Context) {
            itemView.imgTitle.text = imageModel?.name ?: ""
            imageModel?.image_url?.let {
                GlideApp.with(context)
                    .load(it)
                    .into(itemView.imgItem)
            }

            itemView.imgTitle.setOnClickListener {
                selectionListener(context, imageModel)
            }
        }

        private fun selectionListener(context: Context, model: ImageModel?) {
            model?.let {
                val intent = Intent(context, TeamActivity::class.java)
                intent.putExtra("project_id", it.id.toInt())
                context.startActivity(intent)
            }
        }
    }
}