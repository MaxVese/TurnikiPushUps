package com.wallet.turnikipushups.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM

class SchedulerNotification {

    private var alarmManager:AlarmManager? = null
    val requestCode = 3141245

    fun setNotification(context: Context,timeInMillis:Long,delay:Long){
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        val alarmIntent = Intent(context,AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            alarmIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        if(checkAlarmsAccess(context)) {
            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                AlarmManager.INTERVAL_DAY*delay,
                pendingIntent
            )
        }else{
            Intent().apply {
                action = ACTION_REQUEST_SCHEDULE_EXACT_ALARM
            }.also {
                context.startActivity(it)
            }
        }
    }

    fun cancelAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager?
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                SchedulerNotification().requestCode,
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )

        pendingIntent?.let { _pendingIntent->
            alarmManager?.cancel(_pendingIntent)
        }
    }


    private fun checkAlarmsAccess(context: Context) : Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S){
            return true
        }
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager?
        return alarmManager?.canScheduleExactAlarms() == true
    }
}