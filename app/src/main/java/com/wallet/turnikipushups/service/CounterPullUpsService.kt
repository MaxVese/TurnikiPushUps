package com.wallet.turnikipushups.service

import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log


class CounterPullUpsService: Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    companion object{
        var ADD_COUNT = "action.addCount"
    }


    private var mSensorManager: SensorManager? = null
    private var mSensor: Sensor? = null

    var count = 0
    var last_x = 0f
    var last_y = 0f
    var last_z = 0f
    var last_speed = 0f
    var lastUpdate: Long = 0


    override fun onCreate() {
        super.onCreate()
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    private val mGravitySensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val curTime = System.currentTimeMillis()

            if (curTime - lastUpdate > 200) {
                lastUpdate = curTime
                val speed: Float = (x + y + z - last_x - last_y - last_z) / 3


                if (speed - last_speed < -6f && Math.abs(speed) / last_speed < 1) {
                    count++
                    Log.d("mylog",count.toString())
                    sendBroadcast(Intent(ADD_COUNT))
                }
                last_x = x
                last_y = y
                last_z = z
                last_speed = speed
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            Log.d("MY_APP", "$sensor - $accuracy")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mSensorManager?.registerListener(mGravitySensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mSensorManager?.unregisterListener(mGravitySensorListener)
        super.onDestroy()
    }
}