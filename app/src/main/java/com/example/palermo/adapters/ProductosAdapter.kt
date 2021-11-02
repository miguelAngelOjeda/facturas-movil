package com.example.palermo.adapters

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palermo.R
import com.example.palermo.models.Productos
import com.example.palermo.utils.*
import kotlinx.android.synthetic.main.fragment_facturas.*
import kotlinx.android.synthetic.main.list_item_datos_productos.view.*
import kotlinx.android.synthetic.main.list_item_facturas.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton


open class ProductosAdapter(val context: Context, val fm: FragmentManager, val repo: DataRepo, val lista: List<Productos>, val categorias: MutableList<String>, val productos: MutableList<String>) : RecyclerView.Adapter<ProductosAdapter.ViewHolder>() {

    private val data
        get() = lista

    //val categorias : MutableList<String> = ArrayList()
    //val productos : MutableList<String> = ArrayList()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position]!!, position)

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = parent.inflate(R.layout.list_item_datos_productos)
        val watcher = initTextWatcher(itemView)
        itemView.tag = watcher
        return ViewHolder(itemView)
    }

    fun initTextWatcher(itemView: View): ProductoTextWatcher {
        val textWatcher = ProductoTextWatcher(itemView)
        return textWatcher
    }


    fun deleteItem(position: Int) {
        context.alert("Eliminar registro?") {
            yesButton {
                data.filterIndexed { index, value -> index != position }
                notifyDataSetChanged()
            }
            noButton {  }
        }.show()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(producto: Productos, position: Int) {
            val watcher = view.tag as ProductoTextWatcher
            watcher.updatePosition(position)
            removeWatchers(watcher)
            view.spinnerCategoria.adapter = getArrayAdapterFor(categorias)
            view.spinnerCategoria.onItemChanged {
                val codigo = categorias[it].split("/").first().trim()
                producto.codCategoria = codigo
            }
            producto?.codCategoria?.let {
                val codCategoria = it;
                categorias.forEachIndexed{index, item ->
                    val codigo = item.split("/").first().trim()
                    if(codigo.equals(codCategoria)){
                        view.spinnerCategoria.setSelection(index)
                    }
                }
            }

            view.spinnerProducto.adapter = getArrayAdapterFor(productos)
            view.spinnerProducto.onItemChanged {
                val codigo = productos[it].split("/").first().trim()
                producto.codigo = codigo
                if (producto.cantidad != null){
                    repo.getProductoPrecio(producto.codigo, producto.cantidad!!).subscribe( {
                        d("termino el get")
                        if(it.status == 200){
                            view.textPU.setText(it.model?.precio.toStringOCero())
                            view.textTotalPro.setText(it.model?.total.toStringOCero())
                            producto.precioUnitario = it.model?.precio
                            producto.total = it.model?.total
                        }
                    }, {
                        Log.d("Hola", "error", it)
                    })
                }

            }
            producto?.codigo?.let {
                val codCategoria = it;
                productos.forEachIndexed{index, item ->
                    val codigo = item.split("/").first().trim()
                    if(codigo.equals(codCategoria)){
                        view.spinnerProducto.setSelection(index)
                    }
                }
            }
            view.editCantidad.setText(producto.cantidad.toString())
            view.editCantidad.doOnTextChanged { text,start, count, after ->
                if (!text.isNullOrEmpty()){
                    repo.getProductoPrecio(producto.codigo, Integer.parseInt(text.toString())).subscribe( {
                        d("termino el get")
                        if(it.status == 200){
                            view.textPU.setText(it.model?.precio.toStringOCero())
                            view.textTotalPro.setText(it.model?.total.toStringOCero())
                            producto.precioUnitario = it.model?.precio
                            producto.total = it.model?.total
                        }
                    }, {
                        Log.d("Hola", "error", it)
                    })
                }
            }
            producto?.precioUnitario?.let { view.textPU.setText(it.toStringOCero()) }
            producto?.total?.let { view.textTotalPro.setText(it.toStringOCero()) }
            //view.editModelo.setText(producto.codigo)

            view.botonBorrar.setOnClickListener { deleteItem(adapterPosition) }
            addWatchers(watcher)
        }

        private fun removeWatchers(textWatcher: ProductoTextWatcher) {
            view.editCantidad.removeTextChangedListener(textWatcher)
        }

        fun addWatchers(textWatcher: ProductoTextWatcher) {
            view.editCantidad.addTextChangedListener(textWatcher)
        }
    }

    private fun getArrayAdapterFor(datos: MutableList<String>): ArrayAdapter<String> {
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, datos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }


    inner class ProductoTextWatcher(val view: View) : CustomTextWatcher() {
        override fun textChanged() {
            data[currentIndex]?.let { producto ->
                producto.cantidad = view.editCantidad.text?.toIntOCero()
                producto.precioUnitario = view.textPU.text?.toString()?.toDoubleOrNull()
            }

        }
    }
}



