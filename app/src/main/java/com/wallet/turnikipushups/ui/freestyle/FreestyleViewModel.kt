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

class FreestyleViewModel @Inject constructor(val statPushUpsDao: StatPushUpsDao,appSharedPreferense: AppSharedPreferense)
    : ViewModel() {

    val count: MutableLiveData<Int> = MutableLiveData(0)
    val isFinish: MutableLiveData<Boolean> = MutableLiveData(false)
    var isServiceStart: Boolean = false

    fun saveWorkout(){
        val statPushUps = StatPushUps(count = count.value?:0, dateWorkout = LocalDateTime.now())
        statPushUpsDao
            .insert(statPushUps)
        isFinish.value = true
    }
}