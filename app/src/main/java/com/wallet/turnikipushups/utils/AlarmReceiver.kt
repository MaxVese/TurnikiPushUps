package com.wallet.turnikipushups.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.let { NotificationHelper(it) }?.apply { createNotification() }
    }
}