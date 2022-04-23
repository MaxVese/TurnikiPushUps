package com.wallet.turnikipushups.di.component

import android.app.Application
import com.wallet.turnikipushups.activities.StartActivity
import com.wallet.turnikipushups.di.modules.DBModule
import com.wallet.turnikipushups.di.modules.ViewModelModule
import com.wallet.turnikipushups.ui.freestyle.FreestyleViewModel
import com.wallet.turnikipushups.ui.main.MainViewModel
import com.wallet.turnikipushups.ui.notification.NotificationViewModel
import com.wallet.turnikipushups.ui.settings.SettingsViewModel
import com.wallet.turnikipushups.ui.statistics.StatisticsViewModel
import com.wallet.turnikipushups.ui.workout.ChooseLevelViewModel
import com.wallet.turnikipushups.ui.workout.FinallWorkoutViewModel
import com.wallet.turnikipushups.ui.workout.StartWorkoutViewModel
import com.wallet.turnikipushups.ui.workout.WorkoutViewModel
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

    fun inject(startActivity: StartActivity)

    fun mainViewModel(): MainViewModel

    fun startWorkoutViewModel(): StartWorkoutViewModel

    fun workoutViewModel(): WorkoutViewModel

    fun finallWorkoutViewModel(): FinallWorkoutViewModel

    fun chooseLevelViewModel(): ChooseLevelViewModel

    fun settingsViewModel(): SettingsViewModel

    fun statisticsViewModel(): StatisticsViewModel

    fun notificationViewModel(): NotificationViewModel

    fun freestyleViewModel(): FreestyleViewModel

}