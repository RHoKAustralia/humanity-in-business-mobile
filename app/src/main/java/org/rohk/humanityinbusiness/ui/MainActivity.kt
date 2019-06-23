package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.rohk.humanityinbusiness.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        val adapter = DashboardAdapter(View.OnClickListener {
            startActivity(Intent(this, ChallengeActivity::class.java))
        })

        recycler_view.adapter = adapter

        val mToolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(mToolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        val collapsingToolbarLayout = findViewById(R.id.toolbar_layout) as CollapsingToolbarLayout
        val appBarLayout = findViewById(R.id.app_bar) as AppBarLayout
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = true
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title = "Let's start a challenge"
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title =
                        " "//careful there should a space between double quote otherwise it wont work
                    isShow = false
                }
            }
        })
    }
}