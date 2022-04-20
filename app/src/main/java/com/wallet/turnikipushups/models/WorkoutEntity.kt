package com.wallet.turnikipushups.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workoutTable")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val lvl:Int,
    val subLevel:Int,
    val restTime:Int,
    val listReps: List<Int>,
    val lvlOfTraining:LevelOfTraining
)
