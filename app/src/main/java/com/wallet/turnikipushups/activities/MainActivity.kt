package com.wallet.turnikipushups.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wallet.turnikipushups.R
import de.raphaelebner.roomdatabasebackup.core.RoomBackup

class MainActivity : AppCompatActivity() {

    val backup:RoomBackup = RoomBackup(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}