package com.wallet.turnikipushups.ui.notification

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.util.Log
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.databinding.FragmentNotificationBinding
import com.wallet.turnikipushups.ui.BaseFragment
import java.util.*


class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentNotificationBinding {
        return FragmentNotificationBinding.inflate(inflater)
    }

    val dateAndTime = Calendar.getInstance();


    override fun initView() {
        withBinding {
            toolbar.setOnClickListener {
                findNavController().popBackStack()
            }
            timeL.setOnClickListener {
                showTimePicker{ _, hourOfDay, minute ->
                    Log.d("mylog","$hourOfDay:$minute")
                }
            }
        }
    }

    private fun showTimePicker(timeSetListener: OnTimeSetListener){
        val timePicker = TimePickerDialog(requireActivity(), timeSetListener,
            dateAndTime.get(Calendar.HOUR_OF_DAY),
            dateAndTime.get(Calendar.MINUTE), true)
        timePicker.show()
        timePicker.getButton(TimePickerDialog.BUTTON_NEGATIVE).apply {
            setTextColor(resources.getColor(R.color.gray_dark,null))
            setText(R.string.cancel)
        }
        timePicker.getButton(TimePickerDialog.BUTTON_POSITIVE).apply {
            setTextColor(resources.getColor(R.color.blue,null))
            setText(R.string.ok)
        }
        timePicker.window?.setBackgroundDrawable(ResourcesCompat.getDrawable(resources,R.drawable.dialog_bg,null))
    }

}