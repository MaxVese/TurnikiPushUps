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
//    private var mGravitySensor: Sensor? = null
    private var mGyroscopeSensor: Sensor? = null
    private var mSensor: Sensor? = null

    var last_x = 0f
    var rotate_x = 0f
    var rotate_y = 0f
    var rotate_z = 0f
    var last_y = 0f
    var last_z = 0f
    var last_speed = 0f
    var lastUpdateAccelerometr: Long = 0
    var lastUpdateGyroscope: Long = 0
    var last_speed_rotate = 0f
    var gravity:FloatArray = floatArrayOf()


    override fun onCreate() {
        super.onCreate()
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
//        mGravitySensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_GRAVITY)
        mGyroscopeSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

    private val mGravitySensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if(event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
//                val alpha: Float = 0.6f
//                if(gravity.isEmpty()) return
////                val x = event.values[0] - gravity[0]
////                val y = event.values[1] - gravity[1]
////                val z = event.values[2] - gravity[2]
//
//                val x = alpha * gravity[0] + (1 - alpha) * event.values[0]
//                val y = alpha * gravity[1] + (1 - alpha) * event.values[1]
//                val z = alpha * gravity[2] + (1 - alpha) * event.values[2]
//
//
//                val curTime = System.currentTimeMillis()
//
//                if (curTime - lastUpdateAccelerometr > 200) {
//                    lastUpdateAccelerometr = curTime
//                    val speed: Float = (x + y + z - last_x - last_y - last_z)
//
//                    val deltaSpeed = speed - last_speed
//
//                    val speedRound = Math.round(speed)
//
////                    Log.d("mylog",speedRound.toString())
//
//                    if (deltaSpeed < -2.5f && Math.abs(speed / last_speed) < 1f && Math.abs(last_speed_rotate) <= 2f) {
//                        sendBroadcast(Intent(ADD_COUNT))
//                    }
//                    last_x = x
//                    last_y = y
//                    last_z = z
//                    last_speed = speed

                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    val curTime = System.currentTimeMillis()

                    if (curTime - lastUpdateAccelerometr > 200) {
                        lastUpdateAccelerometr = curTime
                        val speed: Float = (x + y + z - last_x - last_y - last_z) / 3


                        if (speed - last_speed < -3f && Math.abs(speed) / last_speed < 1 && last_speed_rotate < 2f) {
                            sendBroadcast(Intent(ADD_COUNT))
                        }
                        last_x = x
                        last_y = y
                        last_z = z
                        last_speed = speed
                }
            }
//            if(event.sensor.type == Sensor.TYPE_GRAVITY) {
//                gravity = event.values
//                val blead1 = (gravity[0] * Math.cos(rotate_x.toDouble()))
//                val blead2 = (gravity[1] * Math.cos(rotate_y.toDouble()))
//                val blead3 = (gravity[2] * Math.cos(rotate_z.toDouble()))
//                val sum = Math.abs(blead1) + Math.abs(blead2) +  Math.abs(blead3)
//                Log.d("mylog","$blead1/$blead2/$blead3/$sum")
//            }
            if(event.sensor.type == Sensor.TYPE_ROTATION_VECTOR){
                val curTime = System.currentTimeMillis()
                if (curTime - lastUpdateGyroscope > 200) {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]
                    rotate_x = x
                    rotate_y = y
                    rotate_z = z
                    Log.d("mylog",rotate_y.toString())
                    last_speed_rotate = (x + y + z)
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            Log.d("MY_APP", "$sensor - $accuracy")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mSensorManager?.registerListener(mGravitySensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
//        mSensorManager?.registerListener(mGravitySensorListener, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        mSensorManager?.registerListener(mGravitySensorListener, mGyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mSensorManager?.unregisterListener(mGravitySensorListener)
        super.onDestroy()
    }
}