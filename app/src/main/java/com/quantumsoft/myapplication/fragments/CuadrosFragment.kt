package com.quantumsoft.myapplication.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.quantumsoft.myapplication.AdapterRecyclerView
import com.quantumsoft.myapplication.FragmentChanger
import com.quantumsoft.myapplication.R
import com.quantumsoft.myapplication.model.data.Pintura
import com.quantumsoft.myapplication.viewmodel.MuseoViewModel
import java.util.ArrayList

class CuadrosFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterRecyclerView: AdapterRecyclerView
    private lateinit var items: MutableList<AdapterRecyclerView.Item>


    companion object {
        lateinit var fragmentChanger: FragmentChanger
        lateinit var museoViewModel: MuseoViewModel

        @JvmStatic
        fun newInstance(a: String, b: String, viewmodel: MuseoViewModel, fragmentChanger: FragmentChanger): CuadrosFragment {
            val fragment = CuadrosFragment()
            CuadrosFragment.fragmentChanger = fragmentChanger
            val args = Bundle()
            fragment.arguments = args
            museoViewModel = viewmodel
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mParam1 = it.getString(mParam1)
            mParam2 = it.getString(mParam2)
        }





    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cuadros, container, false)

        val searchView: SearchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // ITEMS DE EJEMPLO
        items = ArrayList()
//        items.add(AdapterRecyclerView.Item(1, "Carpintero de Nidos", "Sala N° X", "Daniel Gallegos Esquivias"))
//        items.add(AdapterRecyclerView.Item(1, "Carpintero de Nidos", "Sala N° X", "Daniel Gallegos Esquivias"))
//        items.add(AdapterRecyclerView.Item(1, "Carpintero de Nidos", "Sala N° X", "Daniel Gallegos Esquivias"))
//        items.add(AdapterRecyclerView.Item(1, "Carpintero de Nidos", "Sala N° X", "Daniel Gallegos Esquivias"))
//        items.add(AdapterRecyclerView.Item(1, "Carpintero de Nidos", "Sala N° X", "Daniel Gallegos Esquivias"))
//        items.add(AdapterRecyclerView.Item(1, "Carpintero de Nidos", "Sala N° X", "Daniel Gallegos Esquivias"))
//        items.add(AdapterRecyclerView.Item(1, "Carpintero de Nidos", "Sala N° X", "Daniel Gallegos Esquivias"))
//        items.add(AdapterRecyclerView.Item(1, "Carpintero de Nidos", "Sala N° X", "Daniel Gallegos Esquivias"))

        museoViewModel.addExampleData()
        adapterRecyclerView = AdapterRecyclerView(items, AdapterRecyclerView.OnImageClickListener { imageId ->
            fragmentChanger.changeFragment(CuadroDetalleFragment.newInstance("", ""))
        })
        recyclerView.adapter = adapterRecyclerView

        museoViewModel.updateListItems()
        museoViewModel.items.observe(viewLifecycleOwner) {
            adapterRecyclerView.updateList(it)
        }

        return view
    }
}
