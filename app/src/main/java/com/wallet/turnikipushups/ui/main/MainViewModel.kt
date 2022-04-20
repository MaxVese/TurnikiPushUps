package com.wallet.turnikipushups.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.db.dao.WorkoutDao
import com.wallet.turnikipushups.models.LevelOfTraining
import com.wallet.turnikipushups.models.StatPushUps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class MainViewModel @Inject constructor(val statPushUpsDao: StatPushUpsDao,val workoutDao: WorkoutDao)
    : ViewModel() {

    val statsPushUps: MutableLiveData<List<StatPushUps>> = MutableLiveData()

    init {
        viewModelScope.launch {
            workoutDao.getAllByLvl(LevelOfTraining.BEGINNER).collect { Log.d("mylog",it.toString()) }
        }
    }

    fun getAllStats(){
        viewModelScope.launch {
            statPushUpsDao
                .getAll()
                .collect {
                    withContext(Dispatchers.Main){
                        statsPushUps.value = it
                    }
                }
        }
    }

    fun getCountWorkouts():Long{
        return statPushUpsDao.getCountWorkouts()
    }

    fun getBestCount():Int{
        return statPushUpsDao.getBestCount()
    }

    fun getAverageCount():Int{
        return statPushUpsDao.getAverageCount()
    }
}