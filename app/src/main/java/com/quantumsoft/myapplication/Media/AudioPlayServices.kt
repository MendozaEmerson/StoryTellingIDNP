package com.quantumsoft.myapplication.Media

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.quantumsoft.myapplication.R
import com.quantumsoft.myapplication.fragments.StorytellingFragment.Companion.ACTION_AUDIO_FINISHED

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
        private const val NOTIFICATION_ID = 1
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
                    // Inicia el servicio en primer plano después de iniciar la reproducción
                    startForeground(NOTIFICATION_ID, createNotification())
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
                mediaPlayer?.setOnCompletionListener {
                    Log.d(TAG, "audioPlay: Audio finalizado")
                    audioStop() // Detén la reproducción y libera recursos
                    stopSelf() // Detén el servicio
                    sendBroadcast(Intent(ACTION_AUDIO_FINISHED)) // Envía el broadcast
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
        stopForeground(STOP_FOREGROUND_REMOVE) // Detiene el servicio en primer plano y elimina la notificación
    }

    override fun onDestroy() {
        super.onDestroy()
        audioStop()
    }

    private fun createNotification(): Notification {
        val channelId = "audio_playback_channel" // ID del canal de notificación
        val channelName = "Music Service Channel" // Nombre del canal

        // Crea el canal de notificación (necesario para Android 8.0 y superior)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Crea la acción para detener la reproducción
        val stopIntent = Intent(this, AudioPlayServices::class.java).apply {
            putExtra(AudioPlayServices.COMMAND, AudioPlayServices.STOP)
        }
        val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        // Crea la notificación
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Reproduciendo audio en segundo plano") // Título de la notificación
            .setContentText("Audio en reproducción") // Texto de la notificación
            .setSmallIcon(R.drawable.ic_button_play) // Ícono de la notificación
            .setOngoing(true) // La notificación no se puede descartar
            .addAction(R.drawable.ic_button_pause, "Detener", stopPendingIntent) // Agrega la acción

        return notificationBuilder.build()
    }
}
