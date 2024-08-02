package com.quantumsoft.myapplication.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quantumsoft.myapplication.R
import com.quantumsoft.myapplication.ui.adapters.AdapterRecyclerView
import com.quantumsoft.myapplication.viewmodel.FragmentChanger
import com.quantumsoft.myapplication.viewmodel.MuseoViewModel

class MapaFragment : Fragment() {

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
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String): MapaFragment {
            val fragment = MapaFragment()
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
        val view = inflater.inflate(R.layout.fragment_mapa, container, false)
        return view
    }
}