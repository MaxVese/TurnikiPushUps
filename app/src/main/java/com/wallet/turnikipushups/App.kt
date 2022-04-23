package com.wallet.turnikipushups

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.wallet.turnikipushups.di.component.AppComponent
import com.wallet.turnikipushups.di.component.DaggerAppComponent

class App : Application() {
    companion object{
        val NOTIFICATION_CHANNEL = "Counter.pushUp.channel"
    }


    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        createChannel()
    }

    private fun createChannel() {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL,
            getString(R.string.app_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.description = getString(R.string.notify)
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            notificationChannel
        )
    }

}