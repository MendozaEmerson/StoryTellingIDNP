package com.quantumsoft.myapplication.ui.fragments
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quantumsoft.myapplication.R
import com.quantumsoft.myapplication.ui.adapters.AdapterRecyclerView
import com.quantumsoft.myapplication.viewmodel.FragmentChanger
import com.quantumsoft.myapplication.viewmodel.MuseoViewModel

class CuadroDetalleFragment : Fragment() {
    private var mParam1: String? = null
    private var mParam2: String? = null

    private lateinit var museoViewModel: MuseoViewModel
    private lateinit var fragmentChanger: FragmentChanger

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

        // Obtener el ViewModel de la actividad asociada
        museoViewModel = ViewModelProvider(requireActivity()).get(MuseoViewModel::class.java)
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String): CuadroDetalleFragment {
            val fragment = CuadroDetalleFragment()
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
        val view = inflater.inflate(R.layout.fragment_cuadro_detalle, container, false)

        val image_cuadro = view.findViewById<ImageView>(R.id.item_image)
        val title_cuadro = view.findViewById<TextView>(R.id.cuadroTituloTextView)
        val autor_cuadro = view.findViewById<TextView>(R.id.autorCuadroTextView)
        val tecnica_cuadro = view.findViewById<TextView>(R.id.tecnicaTextView)
        val description_cuadro = view.findViewById<TextView>(R.id.descripcionCuadro)

        museoViewModel.pinturaActual.observe(viewLifecycleOwner) { pintura ->
            title_cuadro.text = pintura.titulo
            autor_cuadro.text = pintura.autor
            tecnica_cuadro.text = pintura.tecnica
            description_cuadro.text = pintura.descripcion

            museoViewModel.getImage(pintura.imagen_link) { bitmap ->
                image_cuadro.setImageBitmap(bitmap)
            }

            val reproductorAudio = view.findViewById<ImageView>(R.id.reproductorAudioButton)
            reproductorAudio.setOnClickListener {
                museoViewModel.setExposicionPLay(pintura.id)
            }

        }



        return view
    }
}