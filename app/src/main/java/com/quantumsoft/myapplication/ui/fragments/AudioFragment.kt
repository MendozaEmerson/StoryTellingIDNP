package com.quantumsoft.myapplication.ui.fragments

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.quantumsoft.myapplication.R
import com.quantumsoft.myapplication.data.local.entities.Exposicion
import com.quantumsoft.myapplication.services.AudioPlayServices
import com.quantumsoft.myapplication.ui.fragments.StorytellingFragment.Companion.ACTION_AUDIO_FINISHED
import com.quantumsoft.myapplication.viewmodel.FragmentChanger
import com.quantumsoft.myapplication.viewmodel.MuseoViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AudioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioFragment : Fragment() {
    private lateinit var museoViewModel: MuseoViewModel
    private lateinit var fragmentChanger: FragmentChanger
    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentChanger = context as FragmentChanger
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mParam1 = it.getString(ARG_PARAM1)
            mParam2 = it.getString(ARG_PARAM2)
        }
        museoViewModel = ViewModelProvider(requireActivity()).get(MuseoViewModel::class.java)
        Log.d("AudioFragment", "onCreate called")

    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String): AudioFragment {
            val fragment = AudioFragment()
            val args = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_audio, container, false)

        val cuadroTituloTextView = view.findViewById<TextView>(R.id.cuadroTituloTextView)

        val autorCuadro = view.findViewById<TextView>(R.id.autorCuadroTextView)

        val imageCuadro = view.findViewById<ImageView>(R.id.item_image)

        museoViewModel.currentExposicionPLay.observe(viewLifecycleOwner
        ) { expo: Exposicion? ->
            if (expo == null) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
                cuadroTituloTextView.text = expo.titulo
                autorCuadro.text = expo.autor

                museoViewModel.getImage(expo.imagen_link) { bitmap ->
                    imageCuadro.setImageBitmap(bitmap)
                }
            }
        }

        return view
    }










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
                sendCommandToService(AudioPlayServices.PLAY)
            }
            isPlaying = !isPlaying
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

    private fun sendCommandToService(command: String) {
        val audioPlayServiceIntent = Intent(requireContext(), AudioPlayServices::class.java)
        audioPlayServiceIntent.putExtra(
            AudioPlayServices.COMMAND,
            command
        )// Solo envía el nombre del archivo si el comando es PLAY
        if (command == AudioPlayServices.PLAY) {
            if(museoViewModel.currentExposicionPLay.value != null) {
                audioPlayServiceIntent.putExtra(
                    AudioPlayServices.URL,
                    museoViewModel.currentExposicionPLay.value!!.audio_link
                )
            }
        }
        requireContext().startService(audioPlayServiceIntent)
    }
}
