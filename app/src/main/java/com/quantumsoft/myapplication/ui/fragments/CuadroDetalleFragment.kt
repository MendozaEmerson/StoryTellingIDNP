package com.quantumsoft.myapplication.ui.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.quantumsoft.myapplication.R

class CuadroDetalleFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mParam1 = it.getString(ARG_PARAM1)
            mParam2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cuadro_detalle, container, false)

        val reproductorAudioButton: ImageView = view.findViewById(R.id.reproductorAudioButton)

        reproductorAudioButton.setOnClickListener {
            // Aquí se llama al método changeFragment del objeto fragmentChanger
            // en CuadrosFragment. Asegúrate de que fragmentChanger esté accesible
            // y correctamente inicializado en CuadrosFragment.
            CuadrosFragment.fragmentChanger.changeFragment(StorytellingFragment.newInstance("", ""))
        }

        return view
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CuadroDetalleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
