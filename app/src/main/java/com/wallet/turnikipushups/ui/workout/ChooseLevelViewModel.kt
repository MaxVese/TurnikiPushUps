package com.wallet.turnikipushups.ui.workout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.db.dao.WorkoutDao
import com.wallet.turnikipushups.models.LevelOfTraining
import com.wallet.turnikipushups.models.WorkoutEntity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChooseLevelViewModel @Inject constructor(val workoutDao: WorkoutDao, val sharedPref: AppSharedPreferense)
    : ViewModel() {

    val goBack: MutableLiveData<Boolean> = MutableLiveData(false)
    val beginnerList:MutableLiveData<List<WorkoutEntity>> = MutableLiveData()
    val mediumList:MutableLiveData<List<WorkoutEntity>> = MutableLiveData()
    val proList:MutableLiveData<List<WorkoutEntity>> = MutableLiveData()
    var newLvl:Int = getLvl()


    fun getLists(){
        viewModelScope.launch {
            workoutDao.getAllByLvl(LevelOfTraining.BEGINNER)
                .collect { withContext(Main){beginnerList.value = it} }
        }
        viewModelScope.launch {
            workoutDao.getAllByLvl(LevelOfTraining.AMATEUR)
                .collect { withContext(Main){mediumList.value = it} }
        }
        viewModelScope.launch {
            workoutDao.getAllByLvl(LevelOfTraining.PRO)
                .collect { withContext(Main){proList.value = it} }
        }
    }

    fun getLvl():Int = sharedPref.getLvlTrain()

    fun changeLvl(){
        sharedPref.setLvlTrain(newLvl)
        goBack.value = true
    }


}