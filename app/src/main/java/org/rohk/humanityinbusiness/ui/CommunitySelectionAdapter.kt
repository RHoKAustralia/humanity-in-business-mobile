package org.rohk.humanityinbusiness.ui

import android.content.Context
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_goal_selection.view.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.ui.viewmodel.CommunityModel
import org.rohk.humanityinbusiness.utils.GlideApp

class CommunitySelectionAdapter(mainContext: Context, private val clickListener: (CommunityModel) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private val context = mainContext

    private var list = listOf<CommunityModel>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return CommunitySelectionHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_community_selection,
                parent,
                false
            ), clickListener
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val vh = viewHolder as CommunitySelectionHolder
        vh.bindView(list[position], context, position)
    }

    fun setList(listCommunity: List<CommunityModel>) {
        this.list = listCommunity
        notifyDataSetChanged()
    }

    class CommunitySelectionHolder(itemView: View,
                                   private val clickListener: (CommunityModel) -> Unit) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bindView(communityModel: CommunityModel, context: Context, positon: Int) {
            itemView.tvGoalTitle.text = communityModel.name
            GlideApp.with(context)
                .load(imageList[positon])
                .into(itemView.imgGoal)

//            GlideApp.with(context)
//                .load(communityModel.image_url)
//                .into(itemView.imgGoal)
//            itemView.imgGoal.setImageDrawable(ContextCompat.getDrawable(context, imageList[positon]))
            itemView.setOnClickListener {
//                if(!communityModel.isSelected) {
//                    communityModel.isSelected = true
//                    itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_bright))
//                } else {
//                    communityModel.isSelected = false
//                    itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
//                }

                clickListener(communityModel)
            }
        }
        //val imageList = listOf<Int>(R.drawable.finance, R.drawable.technology, R.drawable.hr, R.drawable.marketing)
        val imageList = listOf<String>(
            "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ffundraising.co.uk%2Fwp-content%2Fuploads%2F2017%2F11%2FScreen-Shot-2017-11-06-at-10.26.00.png&f=1&nofb=1",
            "https://i.pinimg.com/736x/23/93/97/23939795a43d84f4a76e8e21b584db1e--ideas-to-raise-money-for-charity-ways-to-raise-money.jpg",
            "https://www.civilsociety.co.uk/uploads/assets/derivatives/article_img_detail_5ba532e110cf7bcee7b0d6e49c027c8b/3738dd94-511c-465b-9505758432937e0f.jpg",
            "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Fessayshark.com%2Fblog%2Fwp-content%2Fuploads%2F2014%2F12%2Fnon-profit2.jpg&f=1&nofb=1",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTORpM1HcyQVcLvf75TBlbUG6liYpmSKDVhI2s3OWLOMCemojsB0g"

            )
    }


}