package com.wallet.turnikipushups.di.modules

import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.ui.freestyle.FreestyleViewModel
import com.wallet.turnikipushups.ui.main.MainViewModel
import com.wallet.turnikipushups.ui.notification.NotificationViewModel
import com.wallet.turnikipushups.ui.statistics.StatisticsViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    fun provideMainViewModel(statPushUpsDao: StatPushUpsDao): MainViewModel = MainViewModel(statPushUpsDao)

    @Singleton
    @Provides
    fun provideStatisticsViewModel(statPushUpsDao: StatPushUpsDao): StatisticsViewModel = StatisticsViewModel(statPushUpsDao)

    @Singleton
    @Provides
    fun provideNotificationViewModel(statPushUpsDao: StatPushUpsDao,appSharedPreferense: AppSharedPreferense): NotificationViewModel = NotificationViewModel(statPushUpsDao,appSharedPreferense)

    @Singleton
    @Provides
    fun provideFreestyleViewModel(statPushUpsDao: StatPushUpsDao,appSharedPreferense: AppSharedPreferense): FreestyleViewModel = FreestyleViewModel(statPushUpsDao,appSharedPreferense)

}