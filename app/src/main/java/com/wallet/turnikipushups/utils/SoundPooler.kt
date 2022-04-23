package com.wallet.turnikipushups.utils

import android.app.Service
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log
import com.wallet.turnikipushups.R

class SoundPooler {

    private var audioManager: AudioManager? = null
    private var volume = 0f
    private var sound = 0
    private var sound2 = 0
    private var sound3 = 0
    private var sound4 = 0
    private var sound5 = 0
    private var soundPool: SoundPool? = null
    private val MAX_STREAMS = 5
    private val streamType = AudioManager.STREAM_MUSIC
    private var loaded = false


    fun initSound(context: Context) {
        audioManager = context.getSystemService(Service.AUDIO_SERVICE) as AudioManager
        val currentVolumeIndex =
            audioManager?.getStreamVolume(streamType)
        val maxVolumeIndex =
            audioManager?.getStreamMaxVolume(streamType)

        volume = (currentVolumeIndex?.div(maxVolumeIndex?.toFloat()?:10f))?.toFloat()?:1f

        val audioAttrib = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        val builder = SoundPool.Builder()
        builder.setAudioAttributes(audioAttrib)
            .setMaxStreams(MAX_STREAMS)

        soundPool = builder.build()

        soundPool?.setOnLoadCompleteListener { soundPool, sampleId, status ->
            loaded = true
        }

        sound = soundPool?.load(context, R.raw.tick, 1)?:0
        sound2 = soundPool?.load(context, R.raw.stop_exercise, 1)?:0
        sound3 = soundPool?.load(context, R.raw.svist, 1)?:0
        sound4 = soundPool?.load(context, R.raw.set_end, 1)?:0
        sound5 = soundPool?.load(context, R.raw.finall_sound, 1)?:0
    }


//    fun initSoundForFinal(context: Context) {
//        audioManager = context.getSystemService(Service.AUDIO_SERVICE) as AudioManager
//        val currentVolumeIndex =
//            audioManager?.getStreamVolume(streamType)
//        val maxVolumeIndex =
//            audioManager?.getStreamMaxVolume(streamType)
//
//        volume = (currentVolumeIndex?.div(maxVolumeIndex?.toFloat()?:10f))?.toFloat()?:1f
//
//        val audioAttrib = AudioAttributes.Builder()
//            .setUsage(AudioAttributes.USAGE_GAME)
//            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//            .build()
//        val builder = SoundPool.Builder()
//        builder.setAudioAttributes(audioAttrib)
//            .setMaxStreams(MAX_STREAMS)
//
//        soundPool = builder.build()
//
//        soundPool?.setOnLoadCompleteListener { soundPool, sampleId, status ->
//            soundPool.play(sound5, volume, volume, 1, 0, 1f)
//        }
//
//        sound5 = soundPool?.load(context, R.raw.finall_sound, 1)?:0
//    }


    fun tickSound() {
//        if (loaded) {
        val leftVolumn = volume
        val rightVolumn = volume
        soundPool!!.play(sound, leftVolumn, rightVolumn, 1, 0, 1f)
//        }
    }

    fun finalRestSound() {
//        if (loaded) {
        val leftVolumn = volume
        val rightVolumn = volume
        soundPool!!.play(sound2, leftVolumn, rightVolumn, 1, 0, 1f)
//        }
    }
    fun svistSound() {
//        if (loaded) {
        val leftVolumn = volume
        val rightVolumn = volume
        soundPool!!.play(sound3, leftVolumn, rightVolumn, 1, 0, 1f)
//        }
    }
    fun setEndSound() {
//        if (loaded) {
        val leftVolumn = volume
        val rightVolumn = volume
        soundPool!!.play(sound4, leftVolumn, rightVolumn, 1, 0, 1f)
//        }
    }
    fun finalSound() {
//        if (loaded) {
        val leftVolumn = volume
        val rightVolumn = volume
        soundPool!!.play(sound5, leftVolumn, rightVolumn, 1, 0, 1f)
//        }
    }
}