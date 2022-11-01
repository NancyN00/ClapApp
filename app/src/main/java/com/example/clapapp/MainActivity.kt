package com.example.clapapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
   //define mediaPlayer instance as lateinit variable
    //we need to reinitialize it every time user clicks stop, therefore remove latenit and assign null
    //this is an object reference variable for the media player
    //in kt possible to give null to var, must make nulable by adding ?. ?is called safe call operator
         private var mediaPlayer: MediaPlayer? = null
         private lateinit var seekBar: SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekBar = findViewById(R.id.etClapping)
        val playButton = findViewById<FloatingActionButton>(R.id.fabplay)
        playButton.setOnClickListener {
          if(mediaPlayer==null){
              //instance and object means the same. check if the media player is null
              //if so create a new object then start media player
              mediaPlayer = MediaPlayer.create(this, R.raw.applauding)
          }
            mediaPlayer?.start()
        }

        val pauseButton = findViewById<FloatingActionButton>(R.id.fabpause)
        pauseButton.setOnClickListener {
           mediaPlayer?.pause()
        }


        val stopButton = findViewById<FloatingActionButton>(R.id.fabstop)
        stopButton.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
        }

    }
    //only component in this
    private fun initializeSeekBar(){
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                TODO("Not yet implemented")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

        })
    }
}