package com.wallet.turnikipushups.di.modules

import android.app.Application
import androidx.room.Room
import com.wallet.turnikipushups.activities.MainActivity
import com.wallet.turnikipushups.db.AppDatabase
import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.db.dao.StatPushUpsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DBModule {

    @Singleton
    @Provides
    internal fun provideAppDatabase(app:Application):AppDatabase{
        return Room.databaseBuilder(
            app.applicationContext,
            AppDatabase::class.java,
            AppDatabase.DB_NAME)
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    internal fun provideStatPushUpsDao(appDatabase: AppDatabase):StatPushUpsDao = appDatabase.getStatPushUpsDao()

    @Singleton
    @Provides
    internal fun provideAppSharedPreferense(app: Application): AppSharedPreferense = AppSharedPreferense().getInstance(app)


}