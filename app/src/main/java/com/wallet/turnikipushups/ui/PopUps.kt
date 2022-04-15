package com.wallet.turnikipushups.ui

import android.R
import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.wallet.turnikipushups.databinding.InfoPullUpPopupBinding

class PopUps {
    fun DetectWirelessCameraPopUp(context: Activity) {
        val customLayout: InfoPullUpPopupBinding =
            InfoPullUpPopupBinding.inflate(LayoutInflater.from(context))
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