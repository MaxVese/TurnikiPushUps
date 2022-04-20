package com.wallet.turnikipushups.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wallet.turnikipushups.db.converters.DateConverter
import com.wallet.turnikipushups.db.converters.LevelOfTrainingConverter
import com.wallet.turnikipushups.db.converters.ListIntConverter
import com.wallet.turnikipushups.db.dao.WorkoutDao
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.models.StatPushUps
import com.wallet.turnikipushups.models.WorkoutEntity

@TypeConverters(DateConverter::class,LevelOfTrainingConverter::class,ListIntConverter::class)
@Database(entities = [StatPushUps::class,WorkoutEntity::class], version = AppDatabase.VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val VERSION = 1

        const val DB_NAME = "push_ups.db"
    }

    abstract fun getStatPushUpsDao():StatPushUpsDao

    abstract fun getWorkoutDao(): WorkoutDao
}