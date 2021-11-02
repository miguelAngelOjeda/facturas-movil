package com.example.palermo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.palermo.R
import com.example.palermo.adapters.FacturaProductosAdapter
import com.example.palermo.adapters.FacturasAdapter
import com.example.palermo.models.Facturas
import com.example.palermo.models.Productos
import com.example.palermo.ui.BaseFragment
import com.example.palermo.utils.DataRepo
import com.example.palermo.utils.d
import com.example.palermo.utils.setup
import com.github.salomonbrys.kodein.instance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_facturas.*
import kotlinx.android.synthetic.main.fragment_facturas_lista.view.*
import kotlinx.android.synthetic.main.list_item_factura_productos.view.*


class ConfFacturasFragment : BaseFragment() {
    val repo: DataRepo by injector.instance()
    val productos : MutableList<Productos> = ArrayList();
    val arrayTutorialType = object : TypeToken<Array<Productos>>() {}.type
    val gson = Gson()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_facturas_lista, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repo.getFacturaNro().subscribe( {
            if(it.status == 200){
                view.textViewFactura.text = it.model
            }
        }, {
            Log.d("Hola", "error", it)
        })
        val json = arguments?.getString("json")
        var tutorials: Array<Productos> = gson.fromJson(json, arrayTutorialType)
        var total:Int = 0;
        tutorials.forEachIndexed  { idx, tut ->
            productos.add(tut);
            total = total + tut.total!!?.toInt();
        }
        view.textViewTotalG.text = total.toString()

        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI(){
        d("hola")
        repo.getFacturas().subscribe( {
            d("termino el get")

            val facturasAdapter = FacturaProductosAdapter(productos);
            recyclerFacturas.setup(activity)
            recyclerFacturas.adapter = facturasAdapter
        }, {
            Log.d("Hola", "error", it)
        })

    }

}