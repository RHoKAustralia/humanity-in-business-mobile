package org.rohk.humanityinbusiness.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.Button
import kotlinx.android.synthetic.main.activity_challenge.*
import org.rohk.humanityinbusiness.R

class ChallengeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        btnJoin.setOnClickListener {
            showDialog()
        }

    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_joined)

        val yesBtn = dialog.findViewById(R.id.btnYes) as Button
        yesBtn.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()

    }
}
