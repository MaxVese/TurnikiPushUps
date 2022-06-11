package com.wallet.turnikipushups

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*

class BillingClientWrapper(context: Context) : PurchasesUpdatedListener {

    private val billingClient = BillingClient
        .newBuilder(context)
        .enablePendingPurchases()
        .setListener(this)
        .build()

    var onPurchaseListener: OnPurchaseListener? = null


    override fun onPurchasesUpdated(billingResult: BillingResult, purchaseList: MutableList<Purchase>?) {
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                if (purchaseList == null) {
                    //to be discussed in the next article
                    onPurchaseListener?.onPurchaseSuccess(null)
                    return
                }

                purchaseList.forEach(::processPurchase)
            }
            else -> {
                //error occured or user canceled
                onPurchaseListener?.onPurchaseFailure(
                    Error(
                        billingResult.responseCode,
                        billingResult.debugMessage
                    )
                )
            }
        }
    }

    private fun processPurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            onPurchaseListener?.onPurchaseSuccess(purchase)

            if (!purchase.isAcknowledged) {
                acknowledgePurchase(purchase) { billingResult ->
                    if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
                        //implement retry logic or try to acknowledge again in onResume()
                    }
                }
            }
        }
    }

    private fun onConnected(block: () -> Unit) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                block()
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    fun queryProducts(listener: OnQueryProductsListener) {
        val skusList = listOf("pro_version")

        queryProductsForType(
            skusList,
            BillingClient.SkuType.INAPP
        ) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                listener.onSuccess(skuDetailsList?: mutableListOf())
            }else{
                listener.onFailure(
                    Error(billingResult.responseCode, billingResult.debugMessage)
                )
            }
        }
    }

    private fun queryProductsForType(
        skusList: List<String>,
        @BillingClient.SkuType type: String,
        listener: SkuDetailsResponseListener
    ) {
        onConnected {
            billingClient.querySkuDetailsAsync(
                SkuDetailsParams.newBuilder().setSkusList(skusList).setType(type).build(),
                listener
            )
        }
    }

    fun purchase(activity: Activity, product: SkuDetails) {
        onConnected {
            activity.runOnUiThread {
                billingClient.launchBillingFlow(
                    activity,
                    BillingFlowParams.newBuilder().setSkuDetails(product).build()
                )
            }
        }
    }

    private fun acknowledgePurchase(
        purchase: Purchase,
        callback: AcknowledgePurchaseResponseListener
    ) {
        onConnected {
            billingClient.acknowledgePurchase(
                AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.purchaseToken)
                    .build(),
                callback::onAcknowledgePurchaseResponse
            )
        }
    }

    fun queryActivePurchases(listener: OnQueryActivePurchasesListener) {
        queryActivePurchasesForType(
            BillingClient.SkuType.INAPP
        ) { billingResult, activeSubsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                listener.onSuccess(activeSubsList)
            } else {
                listener.onFailure(
                    Error(billingResult.responseCode, billingResult.debugMessage)
                )
            }
        }
    }

    private fun queryActivePurchasesForType(
        @BillingClient.SkuType type: String,
        listener: PurchasesResponseListener
    ) {
        onConnected {
            billingClient.queryPurchasesAsync(type, listener)
        }
    }
}

class Error(val responseCode: Int, val debugMessage: String)

interface OnPurchaseListener {
    fun onPurchaseSuccess(purchase: Purchase?)
    fun onPurchaseFailure(error: Error)
}

interface OnQueryProductsListener {
    fun onSuccess(products: List < SkuDetails > )
    fun onFailure(error: Error)
}

interface OnQueryActivePurchasesListener {
    fun onSuccess(activePurchases: List<Purchase>)
    fun onFailure(error: Error)
}