package com.wallet.turnikipushups.ui.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.databinding.FragmentNotificationBinding
import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.ui.BaseFragment
import com.wallet.turnikipushups.ui.PopUps
import com.wallet.turnikipushups.ui.main.MainViewModel
import com.wallet.turnikipushups.utils.AlarmReceiver
import com.wallet.turnikipushups.utils.SchedulerNotification
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject


class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentNotificationBinding {
        return FragmentNotificationBinding.inflate(inflater)
    }

    private val viewModel: NotificationViewModel by viewModels {
        ViewModelFactory{
            getAppComponent(requireContext()).notificationViewModel()
        }
    }



    override fun initView() {
        withBinding {
            viewModel.notificationEnabled.observe(viewLifecycleOwner){
                notifySwitch.isChecked = it
            }
            viewModel.vibrationEnabled.observe(viewLifecycleOwner){
                vibrSwitch.isChecked = it
            }
            viewModel.hourAndMinute.observe(viewLifecycleOwner){
                timeValue.text = "${it.first}:${it.second}"
            }
            viewModel.dayDelay.observe(viewLifecycleOwner){
                delayDayValue.text = getString(R.string.notify_me_every) + " $it " + getString(R.string.day)
            }
            toolbar.setOnClickListener {
                findNavController().popBackStack()
            }
            timeL.setOnClickListener {
                PopUps().showTimePicker(requireActivity()){ _, hourOfDay, minute ->
                    viewModel.setTime(Pair(hourOfDay,minute))
                }
            }
            notifySwitch.setOnCheckedChangeListener { _, b ->
                if(b){
                    createAlarm()
                }else{
                    SchedulerNotification().cancelAlarm(requireContext())
                }
                viewModel.setNotificationEnabled(b)
            }
            delayDayValue.setOnClickListener {
                viewModel.dayDelay.value?.let {
                    PopUps().countDayPopUp(requireActivity(), it) { delay->
                        viewModel.setDayDelay(delay)
                    }
                }
            }
            vibrSwitch.setOnCheckedChangeListener { _, b ->
                viewModel.setVibrationEnabled(b)
            }
        }
    }

    private fun createAlarm(){
        SchedulerNotification().cancelAlarm(requireContext())
        viewModel.dayDelay.value?.let { SchedulerNotification().setNotification(requireContext(),viewModel.getTimeInLocaleDateTime(),it)}
    }

}