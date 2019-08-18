package org.rohk.humanityinbusiness.ui

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.ui.viewmodel.OnboardingPage

class OnboardingPageAdapter(private var pages: List<OnboardingPage>) :
    PagerAdapter() {

    override fun isViewFromObject(view: View, another: Any): Boolean = view == another

    override fun getCount(): Int = pages.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item: OnboardingPage = pages[position]

        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.onboarding_item,container,false)

        val tvDesc: TextView = view.findViewById(R.id.tvDesc)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val imgItem: ImageView = view.findViewById(R.id.imgItem)

        tvTitle.text = item.title
        tvDesc.text = item.description
        imgItem.setImageDrawable(item.image)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}