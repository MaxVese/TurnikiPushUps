package com.wallet.turnikipushups.di.modules

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wallet.turnikipushups.activities.MainActivity
import com.wallet.turnikipushups.db.AppDatabase
import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.db.dao.WorkoutDao
import com.wallet.turnikipushups.models.LevelOfTraining
import com.wallet.turnikipushups.models.WorkoutEntity
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Singleton


@Module
class DBModule {

    lateinit var appDatabase: AppDatabase

    @Singleton
    @Provides
    internal fun provideAppDatabase(app:Application):AppDatabase{
        appDatabase = Room.databaseBuilder(
            app.applicationContext,
            AppDatabase::class.java,
            AppDatabase.DB_NAME)
            .allowMainThreadQueries()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // moving to a new thread
                    GlobalScope.launch {
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(1, 1,1,60, listOf(1,1,1,1),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(2, 1,2, 60, listOf(1,1,1,2),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(3, 1,3, 60, listOf(1,2,2,1),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(4, 1,4, 60, listOf(1,2,2,2),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(5, 1,5, 60, listOf(2,2,2,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(6, 1,6, 60, listOf(2,2,2,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(7, 2,1, 60, listOf(2,2,3,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(8, 2,2, 60, listOf(2,2,3,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(9, 2,3, 60, listOf(2,3,3,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(10, 2,4, 60, listOf(2,4,4,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(11, 3,1, 60, listOf(3,3,4,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(12, 3,2, 60, listOf(3,4,4,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(13, 3,3, 60, listOf(4,4,3,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(14, 3,4, 60, listOf(4,5,4,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(15, 3,5, 60, listOf(4,5,4,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(16, 4,1, 60, listOf(5,5,4,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(17, 4,2, 60, listOf(5,6,4,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(18, 4,3, 60, listOf(6,5,4,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(19, 4,4, 60, listOf(6,5,5,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(20, 5,1, 120, listOf(10,10,10,10),LevelOfTraining.BEGINNER))
                    }
                }
            })
            .build()
        return appDatabase
    }

    @Singleton
    @Provides
    internal fun provideStatPushUpsDao(appDatabase: AppDatabase):StatPushUpsDao = appDatabase.getStatPushUpsDao()

    @Singleton
    @Provides
    internal fun provideWorkoutDao(appDatabase: AppDatabase): WorkoutDao = appDatabase.getWorkoutDao()

    @Singleton
    @Provides
    internal fun provideAppSharedPreferense(app: Application): AppSharedPreferense = AppSharedPreferense().getInstance(app)


}