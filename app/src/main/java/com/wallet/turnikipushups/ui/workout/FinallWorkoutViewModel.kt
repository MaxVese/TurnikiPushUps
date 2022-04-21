package com.wallet.turnikipushups.ui.workout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.db.dao.WorkoutDao
import com.wallet.turnikipushups.models.LevelOfTraining
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FinallWorkoutViewModel @Inject constructor(val workoutDao: WorkoutDao, val sharedPref: AppSharedPreferense)
    : ViewModel() {

    var minLevel : Int = 0
    var maxLevel : Int = 0
    val goBack:MutableLiveData<Boolean> = MutableLiveData(false)

    fun getLvl():Int = sharedPref.getLvlTrain()

    fun getMinMax(levelOfTraining: LevelOfTraining){
        viewModelScope.launch {
            workoutDao
                .getFirstLevel(levelOfTraining)
                .collect {
                    minLevel = it
                }
        }
        viewModelScope.launch {
            workoutDao
                .getLastLevel(levelOfTraining)
                .collect {
                    maxLevel = it
                }
        }
    }

    fun changeLvl(changeValue:Int){
        if(getLvl().plus(changeValue) in minLevel..maxLevel){
            sharedPref.setLvlTrain(getLvl().plus(changeValue))
        }
        goBack.value = true
    }


}