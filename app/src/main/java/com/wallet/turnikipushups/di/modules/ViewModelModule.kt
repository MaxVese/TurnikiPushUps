package com.wallet.turnikipushups.di.modules

import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.ui.main.MainViewModel
import com.wallet.turnikipushups.ui.statistics.StatisticsViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Singleton
    @Provides
    fun provideMainViewModel(statPushUpsDao: StatPushUpsDao): MainViewModel = MainViewModel(statPushUpsDao)

    @Singleton
    @Provides
    fun provideStatisticsViewModel(statPushUpsDao: StatPushUpsDao): StatisticsViewModel = StatisticsViewModel(statPushUpsDao)

}