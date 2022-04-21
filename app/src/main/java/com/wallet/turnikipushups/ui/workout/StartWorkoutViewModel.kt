package com.wallet.turnikipushups.ui.workout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.db.dao.WorkoutDao
import com.wallet.turnikipushups.models.LevelOfTraining
import com.wallet.turnikipushups.models.WorkoutEntity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StartWorkoutViewModel @Inject constructor(val statPushUpsDao: StatPushUpsDao, val workoutDao: WorkoutDao, val sharedPref: AppSharedPreferense)
    : ViewModel() {

    val workout : MutableLiveData<WorkoutEntity> = MutableLiveData()
    val countInThisLvl : MutableLiveData<Int> = MutableLiveData()
    val countLevels : MutableLiveData<Int> = MutableLiveData()

    fun getWorkout(id:Int){
        viewModelScope.launch {
            workoutDao.getWorkout(id).collect {
                withContext(Main){
                    workout.value = it
                }
            }
        }
    }

    fun getLvl():Int = sharedPref.getLvlTrain()

    fun getCountInLvl(lvlOfTraining: LevelOfTraining, lvl:Int){
        viewModelScope.launch {
            workoutDao.getCountInSubLevel(lvlOfTraining, lvl).collect {
                withContext(Main){
                    countInThisLvl.value = it
                }
            }
        }
    }

    fun getCountLevels(lvlOfTraining: LevelOfTraining){
        viewModelScope.launch {
            workoutDao.getCountLevels(lvlOfTraining).collect {
                withContext(Main){
                    countLevels.value = it
                }
            }
        }
    }
}