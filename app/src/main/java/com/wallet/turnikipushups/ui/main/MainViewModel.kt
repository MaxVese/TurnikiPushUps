package com.wallet.turnikipushups.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.models.StatPushUps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class MainViewModel @Inject constructor(val statPushUpsDao: StatPushUpsDao)
    : ViewModel() {

    val count:MutableLiveData<Int> = MutableLiveData(0)

    val statsPushUps: MutableLiveData<List<StatPushUps>> = MutableLiveData()

    fun getAllStats(){
        viewModelScope.launch(IO) {
            statPushUpsDao
                .getAll()
                .collect {
                    withContext(Dispatchers.Main){
                        statsPushUps.value =it
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