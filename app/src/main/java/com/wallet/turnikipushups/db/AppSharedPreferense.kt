package com.wallet.turnikipushups.db

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.wallet.turnikipushups.db.converters.DateConverter
import java.time.LocalDateTime

class AppSharedPreferense {
    val APP_DATA_PREFS = "app_data_prefs"
    private val TIME_NOTIFY = "timeNotify"
    private val DAY_DELAY = "dayDelay"
    private val VIBR_ENABLED = "vibrationEnabled"
    private val NOTIF_ENABLED = "notifEnabled"
    private val mLock = Any()
    private var sp: SharedPreferences? = null

    private object InstanceHolder {
        val INSTANCE: AppSharedPreferense = AppSharedPreferense()
    }

    fun getInstance(context: Context): AppSharedPreferense {
        synchronized(mLock) {
            if (InstanceHolder.INSTANCE.sp == null) {
                init(context.applicationContext)
            }
        }
        return InstanceHolder.INSTANCE
    }

    private fun init(context: Context) {
        InstanceHolder.INSTANCE.sp = context.getSharedPreferences(APP_DATA_PREFS, 0)
        InstanceHolder.INSTANCE.migrate(context)
    }

    private fun migrate(context: Context) {
        var defaultSharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        if (sp!!.all.isEmpty() && defaultSharedPreferences != null) {
            val all = defaultSharedPreferences.all
            if (!all.isEmpty()) {
                val edit = sp!!.edit()
                for (str in all.keys) {
                    val obj = all[str]
                    if (obj != null) {
                        if (obj is Int) {
                            edit.putInt(str, obj.toInt())
                        } else if (obj is String) {
                            edit.putString(str, obj as String?)
                        } else if (obj is Boolean) {
                            edit.putBoolean(str, obj)
                        } else if (obj is Long) {
                            edit.putLong(str, obj.toLong())
                        } else if (obj is Float) {
                            edit.putFloat(str, obj.toFloat())
                        }
                    }
                }
                edit.apply()
                defaultSharedPreferences.edit().clear().apply()
            }
        }
    }


    fun getTimeNotify(): LocalDateTime? {
        return DateConverter().timestampToDate(sp!!.getLong(TIME_NOTIFY,DateConverter().dateToTimestamp(LocalDateTime.now())!!))
    }

    fun setTimeNotify(localDateTime: LocalDateTime) {
        DateConverter().dateToTimestamp(localDateTime)?.let{sp!!.edit().putLong(TIME_NOTIFY,it).apply()}
    }

    fun getDayDelay(): Int {
        return sp!!.getInt(DAY_DELAY,1)
    }

    fun setDayDelay(delay: Int) {
        sp!!.edit().putInt(DAY_DELAY,delay).apply()
    }

    fun isVibrEnabled(): Boolean {
        return sp!!.getBoolean(VIBR_ENABLED,false)
    }

    fun setVibrEnabled(isEnable:Boolean) {
        sp!!.edit().putBoolean(VIBR_ENABLED,isEnable).apply()
    }

    fun isNotifEnabled(): Boolean {
        return sp!!.getBoolean(NOTIF_ENABLED,false)
    }

    fun setNotifEnabled(isEnable:Boolean) {
        sp!!.edit().putBoolean(NOTIF_ENABLED,isEnable).apply()
    }

}
