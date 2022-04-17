package com.wallet.turnikipushups.ui

import android.app.Activity
import android.app.TimePickerDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.content.res.ResourcesCompat
import com.wallet.turnikipushups.databinding.DayCounterPopUpBinding
import com.wallet.turnikipushups.databinding.InfoPullUpPopupBinding
import com.wallet.turnikipushups.databinding.TestPullupPopupBinding
import java.util.*

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
            context.findViewById<View>(android.R.id.content).rootView,
            Gravity.CENTER,
            0,
            -100
        )
    }

    inline fun countDayPopUp(context: Activity, startCount:Int, crossinline dayCount:(Int) -> Unit) {
        val customLayout: DayCounterPopUpBinding =
            DayCounterPopUpBinding.inflate(LayoutInflater.from(context))
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val window = PopupWindow(customLayout.root, width, height, true)
        window.isOutsideTouchable = true
        var count = startCount
        customLayout.counterValue.text = startCount.toString()
        customLayout.plusBtn.setOnClickListener {
            if(count<365) count++
            customLayout.counterValue.text = count.toString()
        }
        customLayout.minusBtn.setOnClickListener {
            if(count>1) count--
            customLayout.counterValue.text = count.toString()
        }
        customLayout.negativeBTN.setOnClickListener {
            window.dismiss()
        }
        customLayout.positiveBtn.setOnClickListener {
            dayCount(count)
            window.dismiss()
        }
        window.contentView = customLayout.root
        window.showAtLocation(
            context.findViewById<View>(android.R.id.content).rootView,
            Gravity.CENTER,
            0,
            -100
        )
    }


    fun showTimePicker(context: Activity,timeSetListener: TimePickerDialog.OnTimeSetListener){
        val dateAndTime = Calendar.getInstance()
        val timePicker = TimePickerDialog(context, timeSetListener,
            dateAndTime.get(Calendar.HOUR_OF_DAY),
            dateAndTime.get(Calendar.MINUTE), true)
        timePicker.show()
        timePicker.getButton(TimePickerDialog.BUTTON_NEGATIVE).apply {
            setTextColor(resources.getColor(com.wallet.turnikipushups.R.color.gray_dark,null))
            setText(com.wallet.turnikipushups.R.string.cancel)
        }
        timePicker.getButton(TimePickerDialog.BUTTON_POSITIVE).apply {
            setTextColor(resources.getColor(com.wallet.turnikipushups.R.color.blue,null))
            setText(com.wallet.turnikipushups.R.string.ok)
        }
        timePicker.window?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(context.resources,
                com.wallet.turnikipushups.R.drawable.dialog_bg,null))
    }
}