package com.wallet.turnikipushups.di.modules

import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.db.dao.WorkoutDao
import com.wallet.turnikipushups.ui.freestyle.FreestyleViewModel
import com.wallet.turnikipushups.ui.main.MainViewModel
import com.wallet.turnikipushups.ui.notification.NotificationViewModel
import com.wallet.turnikipushups.ui.statistics.StatisticsViewModel
import com.wallet.turnikipushups.ui.workout.FinallWorkoutViewModel
import com.wallet.turnikipushups.ui.workout.StartWorkoutViewModel
import com.wallet.turnikipushups.ui.workout.WorkoutViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    fun provideMainViewModel(statPushUpsDao: StatPushUpsDao,workoutDao: WorkoutDao): MainViewModel = MainViewModel(statPushUpsDao,workoutDao)

    @Provides
    fun provideStatisticsViewModel(statPushUpsDao: StatPushUpsDao): StatisticsViewModel = StatisticsViewModel(statPushUpsDao)

    @Provides
    fun provideStartWorkoutViewModel(statPushUpsDao: StatPushUpsDao,workoutDao: WorkoutDao,appSharedPrefer: AppSharedPreferense): StartWorkoutViewModel =
        StartWorkoutViewModel(statPushUpsDao,workoutDao,appSharedPrefer)

    @Provides
    fun provideFinallWorkoutViewModel(workoutDao: WorkoutDao,appSharedPrefer: AppSharedPreferense): FinallWorkoutViewModel =
        FinallWorkoutViewModel(workoutDao,appSharedPrefer)

    @Provides
    fun provideWorkoutViewModel(statPushUpsDao: StatPushUpsDao,workoutDao: WorkoutDao,appSharedPrefer: AppSharedPreferense): WorkoutViewModel =
        WorkoutViewModel(statPushUpsDao,workoutDao,appSharedPrefer)

    @Singleton
    @Provides
    fun provideNotificationViewModel(statPushUpsDao: StatPushUpsDao, appSharedPrefer: AppSharedPreferense): NotificationViewModel = NotificationViewModel(statPushUpsDao,appSharedPrefer)

    @Singleton
    @Provides
    fun provideFreestyleViewModel(statPushUpsDao: StatPushUpsDao,appSharedPrefer: AppSharedPreferense): FreestyleViewModel = FreestyleViewModel(statPushUpsDao,appSharedPrefer)

}