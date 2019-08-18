package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_onboarding.*
import org.rohk.humanityinbusiness.R
import org.rohk.humanityinbusiness.ui.viewmodel.OnboardingPage

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        tvSkip.setOnClickListener {
            startRegistration()
        }

        btnRegister.setOnClickListener {
            startRegistration()
        }

        initAdapter()
    }

    private fun initAdapter() {
        val benefitsPageAdapter = OnboardingPageAdapter(getList())
        dots_tabview.setupWithViewPager(onboarding_benefits_view, true)
        onboarding_benefits_view.adapter = benefitsPageAdapter
    }

    private fun getList() = listOf<OnboardingPage>(
        OnboardingPage(
            "Welcome to HIB!",
            "Where we link professionals to hello with causes using their skill set to make sure you volunteer your time in the most powerful and productive way to get the most out of your volunteering experience and benefit our society.",
            ContextCompat.getDrawable(this, R.drawable.ic_welcome)
        ),
        OnboardingPage(
            "Speaking of Time...",
            "We really value your time and want to make sure you are only contributing to a cause that matches your available time. To do this we have created causes that you can choose which will best fit your time frame. ",
            ContextCompat.getDrawable(this, R.drawable.ic_time)
        ),
        OnboardingPage(
            "We Appreciate You.",
            "While the goal of HIB is all about helping people in need and causes we want to make sure you’re acknowledged. You can collect trophies along the way so you and the rest of the community can see the positive change you have contributed to.",
            ContextCompat.getDrawable(this, R.drawable.ic_appreciate)
        )
    )

    private fun startRegistration() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }
}
