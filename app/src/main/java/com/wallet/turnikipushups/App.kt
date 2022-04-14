package com.wallet.turnikipushups

import android.app.Application
import android.content.Context
import com.wallet.turnikipushups.di.component.AppComponent
import com.wallet.turnikipushups.di.component.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}