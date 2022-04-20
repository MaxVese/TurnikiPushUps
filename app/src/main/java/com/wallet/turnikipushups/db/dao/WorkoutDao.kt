package com.wallet.turnikipushups.db.dao

import androidx.room.*
import com.wallet.turnikipushups.models.LevelOfTraining
import com.wallet.turnikipushups.models.StatPushUps
import com.wallet.turnikipushups.models.WorkoutEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(workoutEntity: WorkoutEntity): Long

    @Update
    fun update(workoutEntity: WorkoutEntity)


    @Query("SELECT * FROM workoutTable where lvlOfTraining == :lvlOfTraining")
    fun getAllByLvl(lvlOfTraining: LevelOfTraining): Flow<List<WorkoutEntity>>
}