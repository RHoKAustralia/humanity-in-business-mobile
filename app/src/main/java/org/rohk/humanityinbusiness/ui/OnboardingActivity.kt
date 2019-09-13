package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
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
            "Better Professional Awards!",
            "Inspire, recognise and reward professionals who do good.",
            ContextCompat.getDrawable(this, R.drawable.ic_appreciate)
        ),
        OnboardingPage(
            "Choose your community which matches your skills",
            "Choose which Better Team Day you are a part of\n" +
                    "Choose the project you have worked on\n" +
                    "Be recognised as a Better Professional! ",
            ContextCompat.getDrawable(this, R.drawable.ic_fight)
        )
    )

    private fun startRegistration() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }
}
