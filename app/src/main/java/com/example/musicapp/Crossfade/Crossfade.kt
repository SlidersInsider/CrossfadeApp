package com.example.musicapp.Crossfade

import android.media.MediaPlayer
import android.view.View
import com.example.musicapp.TaskCrossfade.TaskCrossfade
import com.example.musicapp.bar
import java.util.*
import java.util.concurrent.TimeUnit

class Crossfade(var song1: MediaPlayer, var song2: MediaPlayer, var view: View) {
    private val secToMs = 1000
    private val maxVolume = 1f
    private val minVolume = 0f
    var crossfadeSize = bar.progress + 2
    var crossfadeStep = 1.0 / (crossfadeSize * 10)
    var crossfadeSizeMs = crossfadeSize*secToMs
    var timer: Timer = Timer()
    var period = 100L
    var firstStepFlag = true

    fun play() {
        val duration1 = song1.duration
        val duration2 = song2.duration
        var delay1 = (duration1 - crossfadeSizeMs).toLong()
        val delay2 = (duration2 - 2 * crossfadeSizeMs).toLong()

        song1.start()
        song1.setVolume(maxVolume,maxVolume)
        view.postDelayed(object: Runnable {
            override fun run() {
                if(song1.isPlaying) {
                    fadeSong(song1, song2, delay1)

                    if(firstStepFlag) {
                        delay1 -= crossfadeSizeMs
                        firstStepFlag = false
                    }
                }
                if(song2.isPlaying) {
                    fadeSong(song2, song1, delay2)
                }
                if(true){
                    view.postDelayed(this, 0)
                }
            }
        }, 0)

    }

    fun fadeSong(song1: MediaPlayer, song2: MediaPlayer, delay: Long) {
        val crossfade1 = TaskCrossfade(song1, song2, crossfadeSize, crossfadeStep)
        timer.scheduleAtFixedRate(crossfade1, delay, period)

        TimeUnit.MILLISECONDS.sleep(delay)
        song2.start()
        song2.setVolume(minVolume,minVolume)

        TimeUnit.MILLISECONDS.sleep((crossfadeSizeMs).toLong())
        crossfade1.cancel()
        timer.purge()
    }
}