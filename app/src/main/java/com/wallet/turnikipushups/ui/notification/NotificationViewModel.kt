package com.wallet.turnikipushups.ui.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.db.converters.DateConverter
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import java.time.LocalDateTime
import javax.inject.Inject

class NotificationViewModel @Inject constructor(val statPushUpsDao: StatPushUpsDao,
                                                 var appSharedPreferense:AppSharedPreferense)
    : ViewModel() {

    val hourAndMinute:MutableLiveData<Pair<Int,Int>> = MutableLiveData(appSharedPreferense.getTimeNotify()?.let { Pair(it.hour,it.minute) })
    val dayDelay:MutableLiveData<Int> = MutableLiveData(appSharedPreferense.getDayDelay())
    val vibrationEnabled:MutableLiveData<Boolean> = MutableLiveData(appSharedPreferense.isVibrEnabled())
    val notificationEnabled:MutableLiveData<Boolean> = MutableLiveData(appSharedPreferense.isNotifEnabled())

    fun setTime(hNm:Pair<Int,Int>){
        hourAndMinute.value = hNm
        appSharedPreferense.setTimeNotify(LocalDateTime.now().withHour(hNm.first).withMinute(hNm.second))
    }

    fun setDayDelay(delay:Int){
        dayDelay.value = delay
        appSharedPreferense.setDayDelay(delay)
    }


    fun setVibrationEnabled(isEnable:Boolean){
        if(isEnable != vibrationEnabled.value){
            vibrationEnabled.value = isEnable
            appSharedPreferense.setVibrEnabled(isEnable)
        }
    }

    fun setNotificationEnabled(isEnable:Boolean){
        if(isEnable != notificationEnabled.value){
            notificationEnabled.value = isEnable
            appSharedPreferense.setNotifEnabled(isEnable)
        }
    }

    fun getTimeInLocaleDateTime():Long{
        return hourAndMinute.value?.let { DateConverter().dateToTimestamp(LocalDateTime.now().withHour(it.first).withMinute(it.second)) }?:DateConverter().dateToTimestamp(
            LocalDateTime.now())!!
    }

}