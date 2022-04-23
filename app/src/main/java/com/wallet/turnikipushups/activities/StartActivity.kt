package com.wallet.turnikipushups.activities

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wallet.turnikipushups.App
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.db.dao.WorkoutDao
import com.wallet.turnikipushups.models.LevelOfTraining
import java.util.*
import javax.inject.Inject


class StartActivity : AppCompatActivity() {

    @Inject
    lateinit var workoutDao:WorkoutDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLanguage()
        setContentView(R.layout.activity_splash)
        (application as App).appComponent.inject(this)
        Log.d("mylog",workoutDao.getAllByLvl(LevelOfTraining.BEGINNER).toString())
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        },3000)
    }

    fun loadLanguage() {
        val languageTag = Locale.forLanguageTag(AppSharedPreferense().getInstance(this).getUserLanguage()?:return)
        val res: Resources = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(languageTag)
        Locale.setDefault(languageTag)
        res.updateConfiguration(conf, dm)

    }
}