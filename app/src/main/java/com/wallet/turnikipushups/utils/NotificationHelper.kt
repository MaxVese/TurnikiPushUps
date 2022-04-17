package com.wallet.turnikipushups.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.db.AppSharedPreferense


internal class NotificationHelper(val context: Context) {
    fun createNotification() {
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        mBuilder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.getString(R.string.notification_text))
            .setAutoCancel(false)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "NOTIFICATION_CHANNEL_NAME",
            importance
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        if(AppSharedPreferense().getInstance(context).isVibrEnabled()){
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        }
        mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
        mNotificationManager.createNotificationChannel(notificationChannel)
        mNotificationManager.notify(0, mBuilder.build())
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "10001"
    }

}