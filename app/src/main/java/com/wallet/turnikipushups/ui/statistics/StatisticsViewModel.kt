package com.wallet.turnikipushups.ui.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.models.StatPushUps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(val statPushUpsDao: StatPushUpsDao)
    : ViewModel() {

    val statsPushUps: MutableLiveData<List<StatPushUps>> = MutableLiveData()

    fun getAllStats(){
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                statPushUpsDao
                    .getAll()
                    .collect {
                        withContext(Main){
                            statsPushUps.value =it
                        }
                    }
            }
        }
    }
}