package com.wallet.turnikipushups.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wallet.turnikipushups.models.StatPushUps


@Dao
interface StatPushUpsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(statPushUps: StatPushUps): Long

    @Update
    fun update(statPushUps: StatPushUps)

    @Delete
    fun delete(vararg statPushUps: StatPushUps)

    @Query("SELECT * FROM statPushUps")
    fun getAll(): List<StatPushUps>

    @Query("SELECT COUNT(*) FROM statPushUps WHERE count > 0")
    fun getCountWorkouts():Long

    @Query("SELECT MAX(count) as max FROM statPushUps")
    fun getBestCount():Int

    @Query("SELECT AVG(count) FROM statPushUps")
    fun getAverageCount():Int
}