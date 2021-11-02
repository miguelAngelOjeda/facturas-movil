package com.example.palermo.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.palermo.R
import com.example.palermo.models.Facturas
import com.example.palermo.utils.inflate
import com.example.palermo.utils.toCurrency
import com.example.palermo.utils.toFechaCorta
import io.reactivex.Observable
import kotlinx.android.synthetic.main.list_item_facturas.view.*


open class FacturasAdapter(val valores: List<Facturas>) : RecyclerView.Adapter<FacturasAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(valores[position]!!)

    override fun getItemCount() = valores.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = parent.inflate(R.layout.list_item_facturas)
        return ViewHolder(itemView)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(fac: Facturas) {
            view.textViewDescripcion.text = "FACTURA"
            view.textNumFactura.text = fac.codigo
            view.textTotal.text = fac.total.toCurrency()
            view.textViewFecha.text = fac.fechaCreacion.toFechaCorta()

        }
    }
}