package com.wallet.turnikipushups.ui.freestyle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.models.StatPushUps
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class FreestyleViewModel @Inject constructor(val statPushUpsDao: StatPushUpsDao,val appSharedPreferense: AppSharedPreferense)
    : ViewModel() {

    val count: MutableLiveData<Int> = MutableLiveData(0)
    val isFinish: MutableLiveData<Boolean> = MutableLiveData(false)
    var isServiceStart: Boolean = false
    var isVolumeEnable:MutableLiveData<Boolean> = MutableLiveData(appSharedPreferense.isVolumeEnabled())

    fun saveWorkout(isTest:Boolean){
        if(isTest){
            appSharedPreferense.setLvlTrain(getLvlByReps(count.value?:1))
        }
        val statPushUps = StatPushUps(count = count.value?:0, dateWorkout = LocalDateTime.now())
        statPushUpsDao
            .insert(statPushUps)
        isFinish.value = true
    }


    fun getLvlByReps(count:Int):Int{
        return when (count){
            in 0..2 -> 1
            in 2..4 -> 3
            in 5..6 -> 7
            in 6..8 -> 9
            in 8..10 -> 11
            else -> 16
        }
    }

    fun changeVolume(){
        appSharedPreferense.setVolumeEnabled(!appSharedPreferense.isVolumeEnabled())
        isVolumeEnable.value = appSharedPreferense.isVolumeEnabled()
    }
}