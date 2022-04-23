package com.wallet.turnikipushups.ui.settings

import androidx.lifecycle.ViewModel
import com.wallet.turnikipushups.db.AppDatabase
import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import javax.inject.Inject

class SettingsViewModel @Inject constructor(val appDatabase: AppDatabase,val appSharedPrefer: AppSharedPreferense)
    : ViewModel() {

}