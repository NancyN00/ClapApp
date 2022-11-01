package com.example.clapapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
   //define mediaPlayer instance as lateinit variable
    //we need to reinitialize it every time user clicks stop, therefore remove latenit and assign null
    //this is an object reference variable for the media player
    //in kt possible to give null to var, must make nulable by adding ?. ?is called safe call operator
         private var mediaPlayer: MediaPlayer? = null
         private lateinit var runnable: Runnable   // this is an interface to be implemented by class instances intend to be used
         private lateinit var seekBar: SeekBar
         private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekBar = findViewById(R.id.etClapping)
        handler = Handler(Looper.getMainLooper())
        val playButton = findViewById<FloatingActionButton>(R.id.fabplay)
        playButton.setOnClickListener {
          if(mediaPlayer==null){
              //instance and object means the same. check if the media player is null
              //if so create a new object then start media player
              mediaPlayer = MediaPlayer.create(this, R.raw.applauding)
              initializeSeekBar()
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
             handler.removeCallbacks(runnable)
            seekBar.progress = 0
        }

    }
    //only component in this. fun is kotlin keyword, initialize is fun name
    private fun initializeSeekBar(){
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            //here you must change generated parameter names ie. seekBar, p1 to progress
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }


        })
            //setting duration. we defined mediaplayer with safe call operator .? must do null checks
        // !! is called lot null assertion operator
        val tvPlayer = findViewById<TextView>(R.id.tvPlayer)
        val tvdive =   findViewById<TextView>(R.id.tvdive)
        seekBar.max = mediaPlayer!!.duration
        runnable = Runnable {
            seekBar.progress = mediaPlayer!!.currentPosition

            //duration is used to add the specific duration at which the media is playing, just like when
            //a song is playing and it shows the min, or second or hour
            val playedTime = mediaPlayer!!.currentPosition/1000
            tvPlayer.text = "$playedTime sec"
            val duration = mediaPlayer!!.duration/1000
            val dueTime = duration-playedTime
            tvdive.text = "$dueTime sec"
            handler.postDelayed(runnable, 1000)

        }
        handler.postDelayed(runnable, 1000)
    }
}