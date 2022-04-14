package com.wallet.turnikipushups.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.models.StatPushUps
import javax.inject.Inject

class MainViewModel @Inject constructor(val statPushUpsDao: StatPushUpsDao)
    : ViewModel() {

    val statsPushUps: MutableLiveData<List<StatPushUps>> = MutableLiveData()

        fun getAllStats(){
            statsPushUps.value = statPushUpsDao.getAll()
        }
}