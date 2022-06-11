package com.wallet.turnikipushups.ui.workout

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.wallet.turnikipushups.*
import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.db.dao.WorkoutDao
import com.wallet.turnikipushups.models.LevelOfTraining
import com.wallet.turnikipushups.models.WorkoutEntity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChooseLevelViewModel @Inject constructor(val workoutDao: WorkoutDao, val sharedPref: AppSharedPreferense,val billingClientWrapper: BillingClientWrapper)
    : ViewModel() {

    val goBack: MutableLiveData<Boolean> = MutableLiveData(false)
    val beginnerList:MutableLiveData<List<WorkoutEntity>> = MutableLiveData()
    val mediumList:MutableLiveData<List<WorkoutEntity>> = MutableLiveData()
    val proList:MutableLiveData<List<WorkoutEntity>> = MutableLiveData()
    var newLvl:Int = getLvl()
    val isHavePro:MutableLiveData<Boolean> = MutableLiveData(false)
    var product_purchase:SkuDetails? = null
    val errorMessage:MutableLiveData<String> = MutableLiveData("")

    init {
        billingClientWrapper.onPurchaseListener = object : OnPurchaseListener{
            override fun onPurchaseSuccess(purchase: Purchase?) {
                isHavePro.value = true
            }

            override fun onPurchaseFailure(error: Error) {
                errorMessage.value = error.debugMessage
            }

        }
    }

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

    fun getPurchaseList() {
        billingClientWrapper.queryProducts(object : OnQueryProductsListener {
            override fun onSuccess(products: List<SkuDetails>) {
                product_purchase = products[0]
                Log.d("mylog", products.toString())
                getActivePurchaseList()
            }

            override fun onFailure(error: Error) {
                Log.d("mylog", error.debugMessage)
                errorMessage.value = error.debugMessage
            }

        })
    }

    fun getActivePurchaseList(){
        billingClientWrapper.queryActivePurchases(object : OnQueryActivePurchasesListener {
            override fun onSuccess(activePurchases: List<Purchase>) {
                val isHavePurchase:Boolean = activePurchases.isNotEmpty()
                viewModelScope.launch {
                    withContext(Main){
                        isHavePro.value = isHavePurchase
                    }
                }
            }

            override fun onFailure(error: Error) {
                Log.d("mylog", error.debugMessage+"blead")
                errorMessage.value = error.debugMessage
            }

        })
    }

    fun buyPro(activity:Activity){
        product_purchase?.let { billingClientWrapper.purchase(activity,it) }
    }

    fun getLvl():Int = sharedPref.getLvlTrain()

    fun changeLvl(){
        sharedPref.setLvlTrain(newLvl)
        goBack.value = true
    }


}