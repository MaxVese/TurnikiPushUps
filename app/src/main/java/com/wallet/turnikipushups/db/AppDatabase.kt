package com.wallet.turnikipushups.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wallet.turnikipushups.db.converters.DateConverter
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.models.StatPushUps

@TypeConverters(DateConverter::class)
@Database(entities = [StatPushUps::class], version = AppDatabase.VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    //TODO строчка
    companion object {
        const val VERSION = 1

        const val DB_NAME = "push_ups.db"
    }
    abstract fun getStatPushUpsDao():StatPushUpsDao
}