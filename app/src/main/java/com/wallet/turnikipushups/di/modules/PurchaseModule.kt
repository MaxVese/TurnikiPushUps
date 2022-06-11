package com.wallet.turnikipushups.di.modules

import android.content.Context
import com.wallet.turnikipushups.BillingClientWrapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PurchaseModule {

    @Singleton
    @Provides
    fun provideBillingClientWrapper(context: Context): BillingClientWrapper = BillingClientWrapper(context)
}