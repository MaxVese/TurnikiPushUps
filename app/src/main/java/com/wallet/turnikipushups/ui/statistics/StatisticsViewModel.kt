package com.wallet.turnikipushups.ui.statistics

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import com.wallet.turnikipushups.models.StatPushUps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(val statPushUpsDao: StatPushUpsDao)
    : ViewModel() {

    val statsPushUps: MutableLiveData<List<StatPushUps>> = MutableLiveData()
    val mapStatsPushUps: MutableLiveData<Map<Pair<Int,Int>,List<StatPushUps>>> = MutableLiveData()
    val listMonths: MutableLiveData<List<Pair<Int,Int>>> = MutableLiveData()

    override fun onCleared() {
        Log.d("mylog","5234")
        super.onCleared()
    }

    fun getAllStats(){
        viewModelScope.launch(Dispatchers.IO) {
            statPushUpsDao
                .getAll()
                .collect {
                    ///Делается список месяцев,с первой записи по последнюю
                    val minStatByDate =it.minByOrNull {
                        it.dateWorkout?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()!!
                    }?.dateWorkout
                    val maxStatByDate = it.maxByOrNull {
                        it.dateWorkout?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()!!
                    }?.dateWorkout
                    val listM:MutableList<Pair<Int,Int>> = mutableListOf()
                    if(minStatByDate != null && maxStatByDate != null) {
                        val periodOfMonth = ChronoUnit.MONTHS.between(minStatByDate, maxStatByDate)
                        if(periodOfMonth > 0){
                            for (i in periodOfMonth + 1 downTo 0) {
                                val date = minStatByDate.plusMonths(i)
                                listM.add(Pair(date!!.year, date.monthValue))
                            }
                        }else{
                            listM.add(Pair(minStatByDate.year, minStatByDate.monthValue))
                        }

                    }

                    withContext(Main){
                        statsPushUps.value =it
                        listMonths.value = listM
                    }
                }
        }
    }
}