package com.example.musicapp

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.musicapp.Crossfade.Crossfade
import java.lang.Exception

// Variables
lateinit var bar: SeekBar
lateinit var song1: MediaPlayer
lateinit var song2: MediaPlayer
lateinit var btnCrossfade: Button
lateinit var btnChooseSong1: Button
lateinit var btnChooseSong2: Button
lateinit var crossfadeTextSize: TextView

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bar = findViewById(R.id.crossfade_size)
        crossfadeTextSize = findViewById(R.id.crossfade_text)
        btnChooseSong1 = findViewById(R.id.song1_btn)
        btnChooseSong2 = findViewById(R.id.song2_btn)
        btnCrossfade = findViewById(R.id.crossfade_btn)

        bar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                crossfadeTextSize.setText((bar.progress+2).toString())
                btnChooseSong1.isEnabled = true
                btnChooseSong2.isEnabled = true
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { }
            override fun onStopTrackingTouch(seekBar: SeekBar?) { }
        })

    }

    fun crossfade(view: View) {
        btnChooseSong1.isEnabled = false
        btnChooseSong2.isEnabled = false

        val crossfadeCall = Crossfade(song1, song2, view)
        crossfadeCall.play()

        btnCrossfade.isEnabled = false

    }

    fun chooseSong1(view: View) {
        try {
            bar.isEnabled = false
            song1 = MediaPlayer.create(this, R.raw.song1)
            if(song1.duration <= (bar.progress+2)*1000) {
                throw Exception("Incorrect song")
            }
        }
        catch (error: Exception) {
            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun chooseSong2(view: View) {
        try {
            bar.isEnabled = false
            song2 = MediaPlayer.create(this, R.raw.song4)
            if(song2.duration <= (bar.progress+2)*1000) {
                throw Exception("Incorrect song")
            }
            else{
                btnCrossfade.isEnabled = true
            }
        }
        catch (error: Exception) {
            Toast.makeText(this, "Choose another song!", Toast.LENGTH_SHORT).show()
        }
    }
}

