package com.wallet.turnikipushups.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.wallet.turnikipushups.App
import com.wallet.turnikipushups.R
import java.util.*


class CounterPullUpsService: Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    companion object{
        var ADD_COUNT = "action.addCount"
        const val notificationID = 1

    }


    private var mSensorManager: SensorManager? = null
    private var mGravitySensor: Sensor? = null
    private var mGyroscopeSensor: Sensor? = null
    private var mSensor: Sensor? = null

    var lastUpdateGyroscope: Long = 0
    var last_speed_rotate = 0f
    var gravity:FloatArray = floatArrayOf()

    private var mPushupState = PushupState.STOPPED_TOP

    private enum class Transition {
        UP, DOWN, STOPPED
    }

    private enum class PushupState {
        MOVING_UP, MOVING_DOWN, STOPPED_BOTTOM, STOPPED_TOP
    }

    private val NOISE = 2.0.toFloat()

    private fun unitVector(x: Float, y: Float, z: Float): FloatArray {
        val mag = Math.sqrt(
            Math.pow(x.toDouble(), 2.0) + Math.pow(y.toDouble(), 2.0)
                    + Math.pow(z.toDouble(), 2.0)
        ).toFloat()
        return floatArrayOf(x / mag, y / mag, z / mag)
    }

    fun dotProduct(
        x: Float, y: Float, z: Float, otherX: Float, otherY: Float,
        otherZ: Float
    ): Float {
        return x * otherX + y * otherY + z * otherZ
    }

    fun projection(
        x: Float, y: Float, z: Float, ontoX: Float, ontoY: Float,
        ontoZ: Float
    ): Float {
        val ontoUnitVector = unitVector(ontoX, ontoY, ontoZ)
        return dotProduct(
            x, y, z, ontoUnitVector[0], ontoUnitVector[1],
            ontoUnitVector[2]
        )
    }


    override fun onCreate() {
        super.onCreate()
//        initSound()
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        mGravitySensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_GRAVITY)
        mGyroscopeSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

    private val mGravitySensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if(event.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {
                if(Math.abs(last_speed_rotate) > 2f) return
                if(gravity.isEmpty()) return
                onLinearAccelerometerChanged(event)
            }
            if(event.sensor.type == Sensor.TYPE_GRAVITY) {
                gravity = event.values
            }
            if(event.sensor.type == Sensor.TYPE_GYROSCOPE){
                val curTime = System.currentTimeMillis()
                if (curTime - lastUpdateGyroscope > 200) {
                    lastUpdateGyroscope = curTime
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]
                    last_speed_rotate = (x + y + z)
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            Log.d("MY_APP", "$sensor - $accuracy")
        }
    }

    private fun onLinearAccelerometerChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        // positive = moving down -> stop
        // negative = moving up -> stop
        var proj: Float = projection(x, y, z, gravity[0], gravity[1], gravity[2])
        proj = if (Math.abs(proj) - NOISE > 0.0f) proj else 0.0f
        if (Math.abs(0.0f - proj) > 0.01) {
            if (proj > 0.0f) {
                transition(Transition.DOWN)
            } else {
                transition(Transition.UP)
            }
        } else {
            transition(Transition.STOPPED)
        }
    }

    private fun transition(transition: Transition) {
        val prevState: PushupState = mPushupState
        val newState: PushupState? = getNewPushupState(transition, prevState)
        if (newState != null) {
            handleNewState(prevState, newState)
        }
    }

    private fun getNewPushupState(
        transition: Transition,
        prevState: PushupState
    ): PushupState? {
        var newState: PushupState? = null
        when (prevState) {
            PushupState.MOVING_DOWN -> if (transition == Transition.STOPPED) {
                newState = PushupState.STOPPED_BOTTOM
            }
            PushupState.STOPPED_TOP -> if (transition == Transition.DOWN) {
                newState = PushupState.MOVING_DOWN
            }
            PushupState.STOPPED_BOTTOM -> if (transition == Transition.UP) {
                newState = PushupState.MOVING_UP
            }
            PushupState.MOVING_UP -> if (transition == Transition.STOPPED) {
                newState = PushupState.STOPPED_TOP
            }
        }
        return newState
    }

    private fun handleNewState(prevState: PushupState, newState: PushupState) {
        mPushupState = newState
        if (prevState == PushupState.MOVING_UP
            && newState == PushupState.STOPPED_TOP
        ) {
            incrementPushupCount()
        }
    }

    private fun incrementPushupCount() {
//        if(AppSharedPreferense().getInstance(applicationContext).isVolumeEnabled()) tickSound()
        sendBroadcast(Intent(ADD_COUNT))
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mSensorManager?.registerListener(mGravitySensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
        mSensorManager?.registerListener(mGravitySensorListener, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL)
//        mSensorManager?.registerListener(mGravitySensorListener, mGyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL)
        startFrontend()
        return super.onStartCommand(intent, flags, startId)
    }

    fun startFrontend() {
        startForeground(
            notificationID,
            NotificationCompat.Builder(applicationContext, App.NOTIFICATION_CHANNEL)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Приложение считает подтягивания")
                .build()
        )
    }

    override fun onDestroy() {
        mSensorManager?.unregisterListener(mGravitySensorListener)
        super.onDestroy()
    }
}