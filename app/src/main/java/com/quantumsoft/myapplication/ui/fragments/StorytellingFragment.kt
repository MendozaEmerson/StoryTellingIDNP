package com.quantumsoft.myapplication.ui.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.quantumsoft.myapplication.services.AudioPlayServices
import com.quantumsoft.myapplication.R
import android.Manifest
import android.widget.ImageButton

class StorytellingFragment : Fragment() {

    private var isPlaying = false
    private lateinit var btnToggle: ImageButton // Define el botón a nivel de clase

    // Broadcast receiver para el evento de finalización de la reproducción
    private val audioFinishedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION_AUDIO_FINISHED) {
                isPlaying = false
                btnToggle.setBackgroundResource(R.drawable.play_button_background) // Accede al botón

//               button image btnToggle set src drawable
                btnToggle.setImageResource(R.drawable.ic_button_play)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storytelling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnToggle = view.findViewById(R.id.btn_toggle)

        btnToggle.setOnClickListener {
            if (isPlaying) {
                // Pausar o detener la reproducción
                btnToggle.setBackgroundResource(R.drawable.play_button_background)
                btnToggle.setImageResource(R.drawable.ic_button_play)
                sendCommandToService(AudioPlayServices.PAUSE)
            } else {
                // Iniciar o reanudar la reproducción
                btnToggle.setBackgroundResource(R.drawable.pause_button_background)
                btnToggle.setImageResource(R.drawable.ic_button_pause)
                sendCommandToService(AudioPlayServices.PLAY, "audioVoice2.mp3")
            }
            isPlaying = !isPlaying
        }
        // Solicita los permisos de Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.FOREGROUND_SERVICE),
                REQUEST_CODE_FOREGROUND_SERVICE
            )
        }
    }

    // Registra el BroadcastReceiver cuando el Fragment se reanuda
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(
            audioFinishedReceiver,
            IntentFilter(ACTION_AUDIO_FINISHED),
            RECEIVER_NOT_EXPORTED // Para que el BroadcastReceiver no sea exportado
        )
    }

    // Desregistra el BroadcastReceiver cuando el Fragment se pausa
    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(audioFinishedReceiver)
    }

    private fun sendCommandToService(command: String, filename: String? = null) {
        val audioPlayServiceIntent = Intent(requireContext(), AudioPlayServices::class.java)
        audioPlayServiceIntent.putExtra(
            AudioPlayServices.COMMAND,
            command
        )// Solo envía el nombre del archivo si el comando es PLAY
        if (command == AudioPlayServices.PLAY) {
            filename?.let {
                audioPlayServiceIntent.putExtra(AudioPlayServices.FILENAME, it)
            }
        }
        requireContext().startService(audioPlayServiceIntent)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StorytellingFragment().apply {
                arguments = Bundle().apply {

                }
            }
        // Código para la solicitud de permisos
        private const val REQUEST_CODE_FOREGROUND_SERVICE = 100

        // Accion para el broadcast receiver
        const val ACTION_AUDIO_FINISHED = "com.quantumsoft.myapplication.AUDIO_FINISHED"
    }
}
