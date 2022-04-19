package com.wallet.turnikipushups.di.component

import android.app.Application
import com.wallet.turnikipushups.activities.MainActivity
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.di.modules.DBModule
import com.wallet.turnikipushups.di.modules.ViewModelModule
import com.wallet.turnikipushups.ui.freestyle.FreestyleViewModel
import com.wallet.turnikipushups.ui.main.MainViewModel
import com.wallet.turnikipushups.ui.notification.NotificationViewModel
import com.wallet.turnikipushups.ui.statistics.StatisticsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DBModule::class,ViewModelModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    fun mainViewModel(): MainViewModel

    fun statisticsViewModel(): StatisticsViewModel

    fun notificationViewModel(): NotificationViewModel

    fun freestyleViewModel(): FreestyleViewModel

}