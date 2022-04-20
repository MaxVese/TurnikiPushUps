package com.wallet.turnikipushups.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.wallet.turnikipushups.App
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.db.dao.WorkoutDao
import com.wallet.turnikipushups.models.LevelOfTraining
import javax.inject.Inject

class StartActivity : AppCompatActivity() {

    @Inject
    lateinit var workoutDao:WorkoutDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        (application as App).appComponent.inject(this)
        Log.d("mylog",workoutDao.getAllByLvl(LevelOfTraining.BEGINNER).toString())
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        },3000)
    }
}