package com.quantumsoft.myapplication.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.quantumsoft.myapplication.R
import com.quantumsoft.myapplication.ui.fragments.StorytellingFragment.Companion.ACTION_AUDIO_FINISHED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

//class AudioPlayServices : Service() {
//    private var mediaPlayer: MediaPlayer? = null
//    private var isPaused: Boolean = false
//
//    companion object {
//        const val TAG = "AudioPlayService"
//        const val FILENAME = "FILENAME"
//        const val COMMAND = "COMMAND"
//        const val PLAY = "PLAY"
//        const val PAUSE = "PAUSE"
//        const val RESUME = "RESUME"
//        const val STOP = "STOP"
//        const val TERMINATE_SERVICE = "TERMINATE_SERVICE"
//        const val SHOW_NOTIFICATION = "SHOW_NOTIFICATION"
//        const val HIDE_NOTIFICATION = "HIDE_NOTIFICATION"
//        private const val NOTIFICATION_ID = 1
//        private const val PAUSE_ACTION = "PAUSE_ACTION"
//        private const val PLAY_ACTION = "PLAY_ACTION"
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        val filename = intent?.getStringExtra(FILENAME)
//        val command = intent?.getStringExtra(COMMAND)
//        val action = intent?.action
//
//        Log.d(TAG, "onStartCommand: command=$command, filename=$filename, action=$action")
//
//        when (command) {
//            PLAY -> {
//                if (mediaPlayer == null) { // Crea un nuevo MediaPlayer solo si no existe
//                    Log.d(TAG, "onStartCommand: Playing audio")
//                    audioPlay(filename)
//                    // Inicia el servicio en primer plano después de iniciar la reproducción
//                    startForeground(NOTIFICATION_ID, createNotification())
//                } else if (!isPlaying()) { // Reanuda si existe pero está pausado
//                    Log.d(TAG, "onStartCommand: Resuming audio")
//                    audioResume()
//                    updateNotification()
//                } else {
//                    Log.d(TAG, "onStartCommand: Audio is already playing")
//                }
//            }
//            PAUSE -> {
//                Log.d(TAG, "onStartCommand: Pausing audio")
//                audioPause()
//                updateNotification()
//            }
//            RESUME -> {
//                Log.d(TAG, "onStartCommand: Resuming audio")
//                audioResume()
//                updateNotification()
//            }
//            STOP -> {
//                Log.d(TAG, "onStartCommand: Stopping audio")
//                audioStop()
//            }
//            SHOW_NOTIFICATION -> {
//                startForeground(NOTIFICATION_ID, createNotification())
//            }
//            HIDE_NOTIFICATION -> {
//                stopForeground(true)
//            }
//            TERMINATE_SERVICE -> {
//                Log.d(TAG, "onStartCommand: Terminating service")
//                audioStop()
//                stopSelf()
//            }
//        }
//
//        when (action) {
//            PAUSE_ACTION -> {
//                audioPause()
//                updateNotification()
//            }
//            PLAY_ACTION -> {
//                audioResume()
//                updateNotification()
//            }
//        }
//
//        return START_NOT_STICKY // No reinicia el servicio si se destruye
//    }
//
//    private fun isPlaying(): Boolean {
//        return mediaPlayer?.isPlaying == true
//    }
//
//    private fun audioPlay(filename: String?) {
//        if (filename != null) {
//            try {
//                val assetFileDescriptor = assets.openFd(filename)
//                mediaPlayer = MediaPlayer()
//                mediaPlayer?.apply {
//                    setDataSource(
//                        assetFileDescriptor.fileDescriptor,
//                        assetFileDescriptor.startOffset,
//                        assetFileDescriptor.length
//                    )
//                    assetFileDescriptor.close()
//                    prepare()
//                    setVolume(1f, 1f)
//                    isLooping = false
//                    start()
//                    Log.d(TAG, "audioPlay: Started playing audio $filename")
//                }
//                mediaPlayer?.setOnCompletionListener {
//                    Log.d(TAG, "audioPlay: Audio finalizado")
//                    audioStop() // Detén la reproducción y libera recursos
//                    stopSelf() // Detén el servicio
//                    sendBroadcast(Intent(ACTION_AUDIO_FINISHED)) // Envía el broadcast
//                }
//            } catch (e: Exception) {
//                Log.e(TAG, "audioPlay: Error playing audio", e)
//            }
//        } else {
//            Log.e(TAG, "audioPlay: Filename is null")
//        }
//        isPaused = false
//    }
//
//    private fun audioPause() {
//        mediaPlayer?.pause()
//        isPaused = true
//        Log.d(TAG, "audioPause: Paused audio")
//    }
//
//    private fun audioResume() {
//        if (isPaused) {
//            mediaPlayer?.start()
//            isPaused = false
//            Log.d(TAG, "audioResume: Resumed audio")
//        } else {
//            Log.d(TAG, "audioResume: Audio was not paused")
//        }
//    }
//
//    private fun audioStop() {
//        mediaPlayer?.apply {
//            stop()
//            release()
//        }
//        mediaPlayer = null
//        Log.d(TAG, "audioStop: Stopped audio")
//        stopForeground(STOP_FOREGROUND_REMOVE) // Detiene el servicio en primer plano y elimina la notificación
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        audioStop()
//    }
//
//    private fun createNotification(): Notification {
//        val channelId = "audio_playback_channel" // ID del canal de notificación
//        val channelName = "Music Service Channel" // Nombre del canal
//
//        // Crea el canal de notificación (necesario para Android 8.0 y superior)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
//            val notificationManager = getSystemService(NotificationManager::class.java)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        val pauseIntent = Intent(this, AudioPlayServices::class.java).apply {
//            action = PAUSE_ACTION
//        }
//        val playIntent = Intent(this, AudioPlayServices::class.java).apply {
//            action = PLAY_ACTION
//        }
//        val pausePendingIntent = PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//        val playPendingIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//
////        Pending intend and action to stop this service completly
//        val stopIntent = Intent(this, AudioPlayServices::class.java).apply {
//            putExtra(COMMAND, TERMINATE_SERVICE)
//        }
//        val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//        val stopAction = NotificationCompat.Action.Builder(R.drawable.baseline_volume_off_24, "Stop", stopPendingIntent).build()
//
//
//        val action = if (isPaused) {
//            NotificationCompat.Action.Builder(R.drawable.ic_button_play, "Play", playPendingIntent).build()
//        } else {
//            NotificationCompat.Action.Builder(R.drawable.ic_button_pause, "Pause", pausePendingIntent).build()
//        }
//
//        // Crea la notificación
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setContentTitle("Reproduciendo audio") // Título de la notificación
//            .setContentText("Audio en reproducción") // Texto de la notificación
//            .setSmallIcon(R.drawable.ic_button_play) // Ícono de la notificación
//            .addAction(action) // Añadir acción de pausa/reproducción
//            .addAction(stopAction) // Añadir acción de detener
//            .setOngoing(true) // La notificación no se puede descartar
//
//        return notificationBuilder.build()
//    }
//
//    private fun updateNotification() {
//        val notificationManager = getSystemService(NotificationManager::class.java)
//        notificationManager.notify(NOTIFICATION_ID, createNotification())
//    }
//}

class AudioPlayServices : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var isPaused: Boolean = false

    companion object {
        const val TAG = "AudioPlayService"
        const val URL = "URL"
        const val COMMAND = "COMMAND"
        const val PLAY = "PLAY"
        const val PAUSE = "PAUSE"
        const val RESUME = "RESUME"
        const val STOP = "STOP"
        const val TERMINATE_SERVICE = "TERMINATE_SERVICE"
        const val SHOW_NOTIFICATION = "SHOW_NOTIFICATION"
        const val HIDE_NOTIFICATION = "HIDE_NOTIFICATION"
        private const val NOTIFICATION_ID = 1
        private const val PAUSE_ACTION = "PAUSE_ACTION"
        private const val PLAY_ACTION = "PLAY_ACTION"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.getStringExtra(URL)
        val command = intent?.getStringExtra(COMMAND)
        val action = intent?.action

        Log.d(TAG, "onStartCommand: command=$command, url=$url, action=$action")

        when (command) {
            PLAY -> {
                if (mediaPlayer == null && url != null) { // Crea un nuevo MediaPlayer solo si no existe
                    Log.d(TAG, "onStartCommand: Downloading and playing audio")
                    downloadAndPlayAudio(url)
                    // Inicia el servicio en primer plano después de iniciar la reproducción
                    startForeground(NOTIFICATION_ID, createNotification())
                } else if (!isPlaying()) { // Reanuda si existe pero está pausado
                    Log.d(TAG, "onStartCommand: Resuming audio")
                    audioResume()
                    updateNotification()
                } else {
                    Log.d(TAG, "onStartCommand: Audio is already playing")
                }
            }
            PAUSE -> {
                Log.d(TAG, "onStartCommand: Pausing audio")
                audioPause()
                updateNotification()
            }
            RESUME -> {
                Log.d(TAG, "onStartCommand: Resuming audio")
                audioResume()
                updateNotification()
            }
            STOP -> {
                Log.d(TAG, "onStartCommand: Stopping audio")
                audioStop()
            }
            SHOW_NOTIFICATION -> {
                startForeground(NOTIFICATION_ID, createNotification())
            }
            HIDE_NOTIFICATION -> {
                stopForeground(true)
            }
            TERMINATE_SERVICE -> {
                Log.d(TAG, "onStartCommand: Terminating service")
                stopForeground(true);
                audioStop()
                stopSelf()
            }
        }

        when (action) {
            PAUSE_ACTION -> {
                audioPause()
                updateNotification()
            }
            PLAY_ACTION -> {
                audioResume()
                updateNotification()
            }
        }

        return START_NOT_STICKY // No reinicia el servicio si se destruye
    }

    private fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying == true
    }

    private fun downloadAndPlayAudio(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val audioFile = downloadAudioFromURL(url)
            if (audioFile != null) {
                withContext(Dispatchers.Main) {
                    audioPlay(audioFile.path)
                }
            } else {
                Log.e(TAG, "downloadAndPlayAudio: Failed to download audio")
            }
        }
    }

    private fun downloadAudioFromURL(url: String): File? {
        return try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            val outputFile = File(cacheDir, "temp_audio.mp3")
            val outputStream = FileOutputStream(outputFile)
            val buffer = ByteArray(1024)
            var length: Int
            while (input.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.close()
            input.close()
            outputFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun audioPlay(filePath: String) {
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(filePath)
                prepare()
                setVolume(1f, 1f)
                isLooping = false
                start()
                Log.d(TAG, "audioPlay: Started playing audio from $filePath")
            }
            mediaPlayer?.setOnCompletionListener {
                Log.d(TAG, "audioPlay: Audio finalizado")
                audioStop() // Detén la reproducción y libera recursos
                stopSelf() // Detén el servicio
            }
        } catch (e: Exception) {
            Log.e(TAG, "audioPlay: Error playing audio", e)
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

        val pauseIntent = Intent(this, AudioPlayServices::class.java).apply {
            action = PAUSE_ACTION
        }
        val playIntent = Intent(this, AudioPlayServices::class.java).apply {
            action = PLAY_ACTION
        }
        val pausePendingIntent = PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val playPendingIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        //        Pending intend and action to stop this service completly
        val stopIntent = Intent(this, AudioPlayServices::class.java).apply {
            putExtra(COMMAND, TERMINATE_SERVICE)
        }
        val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val stopAction = NotificationCompat.Action.Builder(R.drawable.baseline_volume_off_24, "Stop", stopPendingIntent).build()


        val action = if (isPaused) {
            NotificationCompat.Action.Builder(R.drawable.ic_button_play, "Play", playPendingIntent).build()
        } else {
            NotificationCompat.Action.Builder(R.drawable.ic_button_pause, "Pause", pausePendingIntent).build()
        }

        // Crea la notificación
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Reproduciendo audio") // Título de la notificación
            .setContentText("Audio en reproducción") // Texto de la notificación
            .setSmallIcon(R.drawable.ic_button_play) // Ícono de la notificación
            .addAction(action) // Añadir acción de pausa/reproducción
            .addAction(stopAction) // Añadir acción de detener
            .setOngoing(true) // La notificación no se puede descartar

        return notificationBuilder.build()
    }

    private fun updateNotification() {
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, createNotification())
    }
}