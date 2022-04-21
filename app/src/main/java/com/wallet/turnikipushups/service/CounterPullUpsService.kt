package com.wallet.turnikipushups.service

import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.NumberPicker
import android.widget.TextView
import java.util.*


class CounterPullUpsService: Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    companion object{
        var ADD_COUNT = "action.addCount"
    }


    private var mSensorManager: SensorManager? = null
    private var mGravitySensor: Sensor? = null
    private var mGyroscopeSensor: Sensor? = null
    private var mSensor: Sensor? = null

//    var last_x = 0f
//    var rotate_x = 0f
//    var rotate_y = 0f
//    var rotate_z = 0f
//    var last_y = 0f
//    var last_z = 0f
//    var last_speed = 0f
//    var lastUpdateAccelerometr: Long = 0
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
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        mGravitySensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_GRAVITY)
        mGyroscopeSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

    private val mGravitySensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if(event.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {
                if(Math.abs(last_speed_rotate) > 2f) return
                onLinearAccelerometerChanged(event)
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
//
//                    val x = event.values[0]
//                    val y = event.values[1]
//                    val z = event.values[2]
//
//                    val curTime = System.currentTimeMillis()
//
//                    if (curTime - lastUpdateAccelerometr > 200) {
//                        lastUpdateAccelerometr = curTime
//                        val speed: Float = (x + y + z - last_x - last_y - last_z) / 3
//
//
//                        if (speed - last_speed < -3f && Math.abs(speed) / last_speed < 1 && last_speed_rotate < 2f) {
//                            sendBroadcast(Intent(ADD_COUNT))
//                        }
//                        last_x = x
//                        last_y = y
//                        last_z = z
//                        last_speed = speed
//                }
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
        sendBroadcast(Intent(ADD_COUNT))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mSensorManager?.registerListener(mGravitySensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
        mSensorManager?.registerListener(mGravitySensorListener, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL)
//        mSensorManager?.registerListener(mGravitySensorListener, mGyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mSensorManager?.unregisterListener(mGravitySensorListener)
        super.onDestroy()
    }
}