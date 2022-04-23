package com.wallet.turnikipushups.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wallet.turnikipushups.models.StatPushUps
import kotlinx.coroutines.flow.Flow


@Dao
interface StatPushUpsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(statPushUps: StatPushUps): Long

    @Update
    fun update(statPushUps: StatPushUps)

    @Delete
    fun delete(vararg statPushUps: StatPushUps)

    @Query("DELETE FROM statPushUps")
    fun deleteAll()


    @Query("SELECT * FROM statPushUps")
    fun getAll(): Flow<List<StatPushUps>>

    @Query("SELECT COUNT(*) FROM statPushUps WHERE count > 0")
    fun getCountWorkouts():Long

    @Query("SELECT MAX(count) as max FROM statPushUps")
    fun getBestCount():Int

    @Query("SELECT AVG(count) FROM statPushUps")
    fun getAverageCount():Int
}