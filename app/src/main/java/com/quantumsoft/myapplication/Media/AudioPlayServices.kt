package com.quantumsoft.myapplication.Media

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class AudioPlayServices : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var isPaused: Boolean = false

    companion object {
        const val TAG = "AudioPlayServices"
        const val FILENAME = "FILENAME"
        const val COMMAND = "COMMAND"
        const val PLAY = "PLAY"
        const val PAUSE = "PAUSE"
        const val RESUME = "RESUME"
        const val STOP = "STOP"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val filename = intent?.getStringExtra(FILENAME)
        val command = intent?.getStringExtra(COMMAND)

        Log.d(TAG, "onStartCommand: command=$command, filename=$filename")

        when (command) {
            PLAY -> {
                if (mediaPlayer == null) { // Crea un nuevo MediaPlayer solo si no existe
                    Log.d(TAG, "onStartCommand: Playing audio")
                    audioPlay(filename)
                } else if (!isPlaying()) { // Reanuda si existe pero está pausado
                    Log.d(TAG, "onStartCommand: Resuming audio")
                    audioResume()
                } else {
                    Log.d(TAG, "onStartCommand: Audio is already playing")
                }
            }
            PAUSE -> {
                Log.d(TAG, "onStartCommand: Pausing audio")
                audioPause()
            }
            RESUME -> {
                Log.d(TAG, "onStartCommand: Resuming audio")
                audioResume()
            }
            STOP -> {
                Log.d(TAG, "onStartCommand: Stopping audio")
                audioStop()
            }
        }

        return START_NOT_STICKY // No reinicia el servicio si se destruye
    }

    private fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying == true
    }

    private fun audioPlay(filename: String?) {
        if (filename != null) {
            try {
                val assetFileDescriptor = assets.openFd(filename)
                mediaPlayer = MediaPlayer()
                mediaPlayer?.apply {
                    setDataSource(
                        assetFileDescriptor.fileDescriptor,
                        assetFileDescriptor.startOffset,
                        assetFileDescriptor.length
                    )
                    assetFileDescriptor.close()
                    prepare()
                    setVolume(1f, 1f)
                    isLooping = false
                    start()
                    Log.d(TAG, "audioPlay: Started playing audio $filename")
                }
            } catch (e: Exception) {
                Log.e(TAG, "audioPlay: Error playing audio", e)
            }
        } else {
            Log.e(TAG, "audioPlay: Filename is null")
        }
        isPaused = false
    }

    private fun audioPause() {
        mediaPlayer?.pause()
        isPaused = true
        Log.d(TAG, "audioPause: Paused audio")
    }

    private fun audioResume() {
        if (isPaused) {
            mediaPlayer?.start()
            isPaused = false
            Log.d(TAG, "audioResume: Resumed audio")
        } else {
            Log.d(TAG, "audioResume: Audio was not paused")
        }
    }

    private fun audioStop() {
        mediaPlayer?.apply {
            stop()
            release()
        }
        mediaPlayer = null
        Log.d(TAG, "audioStop: Stopped audio")
    }

    override fun onDestroy() {
        super.onDestroy()
        audioStop()
    }
}
