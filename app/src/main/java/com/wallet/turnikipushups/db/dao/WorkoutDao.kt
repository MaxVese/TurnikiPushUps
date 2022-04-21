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

    @Query("SELECT * FROM workoutTable where id == :id")
    fun getWorkout(id:Int):Flow<WorkoutEntity>

    @Query("SELECT COUNT(*) FROM workoutTable where lvlOfTraining == :lvlOfTraining and lvl == :lvl")
    fun getCountInSubLevel(lvlOfTraining: LevelOfTraining,lvl:Int): Flow<Int>

    @Query("SELECT MIN(id) FROM workoutTable where lvlOfTraining == :lvlOfTraining")
    fun getFirstLevel(lvlOfTraining: LevelOfTraining): Flow<Int>

    @Query("SELECT MAX(id) FROM workoutTable where lvlOfTraining == :lvlOfTraining")
    fun getLastLevel(lvlOfTraining: LevelOfTraining): Flow<Int>

    @Query("SELECT COUNT(*) FROM workoutTable where lvlOfTraining == :lvlOfTraining group by lvl")
    fun getCountLevels(lvlOfTraining: LevelOfTraining): Flow<Int>
}