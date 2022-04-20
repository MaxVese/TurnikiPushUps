package com.wallet.turnikipushups.db.converters

import androidx.room.TypeConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class ListIntConverter {

    @TypeConverter
    fun fromList(value:List<Int>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value:String) = Json.decodeFromString<List<Int>>(value)
}