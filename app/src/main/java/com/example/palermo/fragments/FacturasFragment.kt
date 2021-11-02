package com.example.palermo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.palermo.R
import com.example.palermo.adapters.FacturasAdapter
import com.example.palermo.models.Facturas
import com.example.palermo.ui.BaseFragment
import com.example.palermo.utils.DataRepo
import com.example.palermo.utils.d
import com.example.palermo.utils.setup
import com.github.salomonbrys.kodein.instance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_facturas.*


class FacturasFragment : BaseFragment() {
    val repo: DataRepo by injector.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_facturas, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun setupUI(){
        d("hola")
        repo.getFacturas().subscribe( {
            d("termino el get")
            val facturasAdapter = FacturasAdapter(it.rows);
            recyclerFacturas.setup(activity)
            recyclerFacturas.adapter = facturasAdapter
        }, {
            Log.d("Hola", "error", it)
        })

    }

}