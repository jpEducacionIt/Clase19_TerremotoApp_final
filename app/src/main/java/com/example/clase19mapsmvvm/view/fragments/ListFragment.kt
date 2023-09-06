package com.example.clase19mapsmvvm.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clase19mapsmvvm.*
import com.example.clase19mapsmvvm.model.data.Terremoto
import com.example.clase19mapsmvvm.model.db.TerremotoDataBase
import com.example.clase19mapsmvvm.model.db.getDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TerremotoAdapter
    private var listQuakes: MutableList<Terremoto> = mutableListOf()
    private lateinit var dataBase: TerremotoDataBase
    private lateinit var progressBar: ProgressBar

    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val myView = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = myView.findViewById(R.id.recyclerView)
        progressBar = myView.findViewById(R.id.progressBar)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBase = getDataBase(requireActivity().applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = TerremotoAdapter()
        recyclerView.adapter = adapter

        //getTerremotoFromDatabase()

        adapter.onItemClickListener = { terremoto ->
            navigateToDetail(terremoto)
        }

        viewModel._listTerromoto.observe(viewLifecycleOwner, Observer { terremotos ->
            insertListadoTerremotos(terremotos)
            adapter.submitList(terremotos)
        })

        viewModel._status.observe(viewLifecycleOwner, Observer { status ->
           when (status) {
               Status.LOADING -> progressBar.visibility = View.VISIBLE
               Status.FAILED -> {
                   progressBar.visibility = View.INVISIBLE
                   Toast.makeText(activity, "Ha fallado el request", Toast.LENGTH_SHORT).show()
               }
               Status.SUCCESS -> progressBar.visibility = View.INVISIBLE
           }
        })
    }

    private fun getTerremotoFromDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            listQuakes = dataBase.terremotoDao.getAllTerremotos()
            adapter.submitList(listQuakes)
        }
    }

    private fun navigateToDetail(terremoto: Terremoto) {
        val bundle = Bundle()
        bundle.putParcelable("terremoto",terremoto)
        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, detailFragment)?.commit()
    }

    private fun insertListadoTerremotos(listQuakes: MutableList<Terremoto>) {
        CoroutineScope(Dispatchers.IO).launch {
            dataBase.terremotoDao.insert(listQuakes)
        }
    }
}