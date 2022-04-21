package com.wallet.turnikipushups.ui.workout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.db.dao.WorkoutDao
import com.wallet.turnikipushups.models.StatPushUps
import com.wallet.turnikipushups.models.WorkoutEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class WorkoutViewModel @Inject constructor(val statPushUpsDao: StatPushUpsDao, val workoutDao: WorkoutDao, val sharedPref: AppSharedPreferense)
    : ViewModel() {

    val workout:MutableLiveData<WorkoutEntity> = MutableLiveData()
    val countReps:MutableLiveData<Pair<Int,Boolean>> = MutableLiveData()
    val currentSet:MutableLiveData<Int> = MutableLiveData()
    val isFinish: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getWorkout(id:Int){
        viewModelScope.launch {
            workoutDao.getWorkout(id).collect {
                withContext(Dispatchers.Main){
                    workout.value = it
                }
            }
        }
    }


    fun saveWorkout(){
        val statPushUps = StatPushUps(count = workout.value?.listReps?.reduce { acc, i -> acc+i }?:0, dateWorkout = LocalDateTime.now())
        statPushUpsDao
            .insert(statPushUps)
        isFinish.value = true
    }
}