package org.rohk.humanityinbusiness.ui

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.rohk.humanityinbusiness.R

class DashboardAdapter(val listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val titles = arrayOf(
        "Challenge of the week",
        "Create a brand guide",
        "Create a practical social media strategy", "Optimise our website SEO", "Design a standard marketing report",
        "Setup/Refine our Google Analytics", "Create a marketing plan "
    )

    private val details = arrayOf(
        "1st July 2019", "9th December 2019",
        "27th July 2019", "5th August 2019",
        "15th August 2019", "12th October 2019",
        "1st July 2019"
    )
    private val points = arrayOf(
        "100 pts, half day", "1000 pts, 2 days",
        "50 pts, 2 hours", "500 pts, full day",
        "100 pts, half day", "1000 pts, 2 days",
        "100 pts, half day"
    )
    private val images = intArrayOf(
        R.drawable.dummy_chlg,
        R.drawable.dummy_chlg, R.drawable.dummy_chlg,
        R.drawable.dummy_chlg, R.drawable.dummy_chlg,
        R.drawable.dummy_chlg, R.drawable.dummy_chlg
    )

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, i: Int) {
        val viewHolder = vh as ViewHolder
        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemDetail.text = details[i]
        viewHolder.itemPoints.text = points[i]
        viewHolder.itemImage.setImageResource(images[i])
    }

    private val clickListener = listener

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView
        var itemPoints: TextView

        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)
            itemPoints = itemView.findViewById(R.id.item_points)

            itemView.setOnClickListener(clickListener)
        }
    }

}