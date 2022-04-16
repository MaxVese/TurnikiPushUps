package com.wallet.turnikipushups.ui

import android.R
import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.wallet.turnikipushups.databinding.InfoPullUpPopupBinding
import com.wallet.turnikipushups.databinding.TestPullupPopupBinding

class PopUps {

    fun infoPullUpPopUp(context: Activity) {
        val customLayout: InfoPullUpPopupBinding =
            InfoPullUpPopupBinding.inflate(LayoutInflater.from(context))
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val window = PopupWindow(customLayout.root, width, height, true)
        window.isOutsideTouchable = true
        customLayout.dismissBtn.setOnClickListener { window.dismiss() }
        window.contentView = customLayout.root
        window.showAtLocation(
            context.findViewById<View>(android.R.id.content).rootView,
            Gravity.CENTER,
            0,
            -100
        )
    }
    fun testPullUpPopUp(context: Activity) {
        val customLayout: TestPullupPopupBinding =
            TestPullupPopupBinding.inflate(LayoutInflater.from(context))
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val window = PopupWindow(customLayout.root, width, height, true)
        window.isOutsideTouchable = true
        customLayout.dismissBtn.setOnClickListener { window.dismiss() }
        window.contentView = customLayout.root
        window.showAtLocation(
            context.findViewById<View>(R.id.content).rootView,
            Gravity.CENTER,
            0,
            -100
        )
    }
}