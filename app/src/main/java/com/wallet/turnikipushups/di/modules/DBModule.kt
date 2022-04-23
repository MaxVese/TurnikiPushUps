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
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(1, 1,1,90, listOf(1,1,1,1),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(2, 1,2, 90, listOf(1,1,1,2),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(3, 1,3, 90, listOf(1,2,2,1),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(4, 1,4, 90, listOf(1,2,2,2),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(5, 1,5, 90, listOf(2,2,2,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(6, 1,6, 90, listOf(2,2,2,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(7, 2,1, 90, listOf(2,2,3,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(8, 2,2, 90, listOf(2,2,3,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(9, 2,3, 90, listOf(2,3,3,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(10, 2,4, 90, listOf(2,4,4,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(11, 3,1, 90, listOf(3,3,4,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(12, 3,2, 90, listOf(3,4,4,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(13, 3,3, 90, listOf(4,4,3,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(14, 3,4, 90, listOf(4,5,4,3),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(15, 3,5, 90, listOf(4,5,4,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(16, 4,1, 90, listOf(5,5,4,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(17, 4,2, 90, listOf(5,6,4,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(18, 4,3, 90, listOf(6,5,4,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(19, 4,4, 90, listOf(6,5,5,4),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(20, 5,1, 120, listOf(10),LevelOfTraining.BEGINNER))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(21, 6,1, 90, listOf(3,5,5,4,3),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(22, 6,2, 90, listOf(4,5,5,4,3),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(23, 6,3, 90, listOf(5,5,5,4,3),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(24, 6,4, 90, listOf(4,6,6,5,3),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(25, 6,5, 90, listOf(5,6,6,5,3),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(26, 7,1, 90, listOf(5,6,5,5,5),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(27, 7,2, 90, listOf(6,6,5,5,5),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(28, 7,3, 90, listOf(6,7,5,5,5),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(29, 7,4, 90, listOf(6,7,5,5,5),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(30, 8,1, 90, listOf(6,7,6,5,5),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(31, 8,2, 90, listOf(7,7,6,5,5),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(32, 8,3, 90, listOf(7,7,6,6,5),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(33, 8,4, 90, listOf(8,7,6,6,5),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(34, 8,5, 90, listOf(8,7,6,6,5),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(35, 9,1, 90, listOf(8,7,6,6,6),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(36, 9,2, 90, listOf(8,7,7,6,5),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(37, 9,3, 90, listOf(8,7,7,6,5),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(38, 9,4, 90, listOf(9,7,7,6,6),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(39, 10,1, 90, listOf(9,7,7,7,6),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(40, 10,2, 90, listOf(9,8,7,7,6),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(41, 10,3, 90, listOf(10,8,7,7,6),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(42, 10,4, 90, listOf(10,8,8,7,6),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(43, 10,5, 90, listOf(10,8,8,7,7),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(44, 11,1, 120, listOf(15),LevelOfTraining.AMATEUR))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(45, 12,1, 90, listOf(10,9,8,7,7),LevelOfTraining.PRO))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(46, 12,2, 90, listOf(10,9,8,8,7),LevelOfTraining.PRO))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(47, 12,3, 90, listOf(10,10,8,8,6),LevelOfTraining.PRO))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(48, 12,4, 90, listOf(10,10,8,8,7),LevelOfTraining.PRO))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(49, 13,1, 90, listOf(10,10,9,8,7),LevelOfTraining.PRO))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(50, 13,2, 90, listOf(11,10,9,8,7),LevelOfTraining.PRO))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(51, 13,3, 90, listOf(11,10,9,8,8),LevelOfTraining.PRO))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(52, 14,1, 90, listOf(11,10,9,9,8),LevelOfTraining.PRO))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(53, 14,2, 90, listOf(11,11,9,9,8),LevelOfTraining.PRO))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(54, 14,3, 90, listOf(12,11,9,9,8),LevelOfTraining.PRO))
                        appDatabase.getWorkoutDao().insert(WorkoutEntity(55, 15,1, 120, listOf(15),LevelOfTraining.PRO))
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