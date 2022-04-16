package com.wallet.turnikipushups.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.wallet.turnikipushups.db.converters.DateConverter
import java.time.LocalDateTime

//TODO за DateConverter респект
@Entity(tableName = "statPushUps")
data class StatPushUps(
    var count:Int = 0,
    @TypeConverters(value = [DateConverter::class])
    var dateWorkout:LocalDateTime? = null
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

}
