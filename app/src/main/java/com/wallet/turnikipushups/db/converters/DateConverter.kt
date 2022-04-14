package com.wallet.turnikipushups.db.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class DateConverter {

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?):Long?{
        return  date?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    }

    @TypeConverter
    fun timestampToDate(timestamp: Long?): LocalDateTime? {
        return if(timestamp!=null) {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        }else null
    }
}