package com.quantumsoft.myapplication.ui.fragments
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.quantumsoft.myapplication.ui.adapters.AdapterRecyclerView
import com.quantumsoft.myapplication.viewmodel.FragmentChanger
import com.quantumsoft.myapplication.R
import com.quantumsoft.myapplication.viewmodel.MuseoViewModel
import java.util.ArrayList

class CuadrosFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterRecyclerView: AdapterRecyclerView
    private lateinit var items: MutableList<AdapterRecyclerView.Item>

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
        fun newInstance(param1: String, param2: String): CuadrosFragment {
            val fragment = CuadrosFragment()
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
        val view = inflater.inflate(R.layout.fragment_cuadros, container, false)

        val searchView: SearchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inicializar la lista de items
        items = mutableListOf()

        // Establecer el adaptador
        adapterRecyclerView = AdapterRecyclerView(items, AdapterRecyclerView.OnImageClickListener { imageId ->
            fragmentChanger.changeFragment(CuadroDetalleFragment.newInstance("", ""))
        }, museoViewModel)
        recyclerView.adapter = adapterRecyclerView

        // Agregar datos de ejemplo y actualizar la lista
//        museoViewModel.addExampleData()
        museoViewModel.updateListItems()

        // Observa el LiveData para actualizar el adaptador
        museoViewModel.items.observe(viewLifecycleOwner) { items ->
            adapterRecyclerView.updateList(items)
        }


        // update search value an filter items list
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                museoViewModel.searchExposiciones(newText ?: "")
                return true
            }
        })

        return view
    }
}