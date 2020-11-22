package com.example.musicapp.TaskCrossfade

import android.media.MediaPlayer
import java.util.*

var i = 0

class TaskCrossfade(var song1: MediaPlayer, var song2: MediaPlayer, var crossfadeSize: Int, var crossfadeStep: Double) : TimerTask() {

    override fun run() {
        if(i < crossfadeSize*10) {
            i++

            song1.setVolume((1.0 - crossfadeStep* i).toFloat(), (1.0 - crossfadeStep* i).toFloat())
            song2.setVolume((crossfadeStep* i).toFloat(),(crossfadeStep* i).toFloat())
        }
        else {
            i = 0
        }
    }
}