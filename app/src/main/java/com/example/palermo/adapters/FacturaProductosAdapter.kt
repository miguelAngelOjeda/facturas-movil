package com.example.palermo.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.palermo.R
import com.example.palermo.models.Facturas
import com.example.palermo.models.Productos
import com.example.palermo.utils.inflate
import com.example.palermo.utils.toCurrency
import com.example.palermo.utils.toFechaCorta
import io.reactivex.Observable
import kotlinx.android.synthetic.main.list_item_factura_productos.view.*


open class FacturaProductosAdapter(val valores: List<Productos>) : RecyclerView.Adapter<FacturaProductosAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(valores[position]!!)

    override fun getItemCount() = valores.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = parent.inflate(R.layout.list_item_factura_productos)
        return ViewHolder(itemView)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(fac: Productos) {
            view.textViewCateg.text = fac.codCategoria
            view.textViewProducto.text = fac.codigo
            view.textCantidad.text = fac.cantidad.toString()
            view.textPU.text = fac.precioUnitario.toString()
            view.textViewTotalP.text = fac.total.toString()
        }
    }
}