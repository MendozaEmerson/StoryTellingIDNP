package com.quantumsoft.myapplication.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.quantumsoft.myapplication.R
import com.quantumsoft.myapplication.data.local.AppDatabase
import com.quantumsoft.myapplication.data.local.entities.Sala
import com.quantumsoft.myapplication.data.repository.SalaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InsertDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InsertDataFragment : Fragment() {

    private lateinit var database: AppDatabase
    private lateinit var salaRepository: SalaRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_insert_data, container, false)
        database = AppDatabase.getDatabase(requireContext())
        salaRepository = SalaRepository(requireContext())

        val etSalaNombre = view.findViewById<EditText>(R.id.et_sala_nombre)
        val etSalaDescripcion = view.findViewById<EditText>(R.id.et_sala_descripcion)
        val etSalaPosX = view.findViewById<EditText>(R.id.et_sala_posX)
        val etSalaPosY = view.findViewById<EditText>(R.id.et_sala_posY)
        val etSalaWidth = view.findViewById<EditText>(R.id.et_sala_width)
        val etSalaHeight = view.findViewById<EditText>(R.id.et_sala_height)

        val btnInsertSala = view.findViewById<Button>(R.id.btn_insert_sala)

        btnInsertSala.setOnClickListener {
            val sala = Sala(
                nombre = etSalaNombre.text.toString(),
                descripcion = etSalaDescripcion.text.toString(),
                posX = etSalaPosX.text.toString().toFloat(),
                posY = etSalaPosY.text.toString().toFloat(),
                width = etSalaWidth.text.toString().toFloat(),
                height = etSalaHeight.text.toString().toFloat()
            )
            CoroutineScope(
                Dispatchers.IO
            ).launch {
                salaRepository.insertSala(sala)


                val salas = salaRepository.getAllSalas()
                salas.forEach {
                    Log.i("Sala", it.toString())
                }
            }
        }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InsertDataFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InsertDataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}