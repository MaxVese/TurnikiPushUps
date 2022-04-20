package com.wallet.turnikipushups.db.converters

import androidx.room.TypeConverter
import com.wallet.turnikipushups.models.LevelOfTraining

class LevelOfTrainingConverter {

    @TypeConverter
    fun toLevelOfTraining(value:String) = enumValueOf<LevelOfTraining>(value)

    @TypeConverter
    fun fromLevelOfTraining(value:LevelOfTraining) = value.name
}