package com.wallet.turnikipushups.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("mylog","notif")
        p0?.let { NotificationHelper(it) }?.apply { createNotification() }
    }
}