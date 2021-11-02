package com.example.palermo.utils

import android.util.Log
import com.example.palermo.models.Categorias
import com.example.palermo.models.Facturas
import com.example.palermo.models.Precios
import com.example.palermo.models.Productos
import com.example.palermo.network.ApiInterface
import com.example.palermo.network.ApiRequest
import coomecipar.example.palermo.auth.ConnectionError
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class DataRepo(val api: ApiInterface) {

    companion object {
        val DEFAULT_PAGE = 2000
    }

    private data class PayLoad(val form: HashMap<String, RequestBody>, val file: MultipartBody.Part? )

    private fun createPartFromString(partString: String): RequestBody {
        return RequestBody.create(MultipartBody.FORM, partString)
    }

    fun getFacturas(): Single<ApiRequest<Facturas>> {
        return api.getFacturas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getFacturaNro(): Single<ApiRequest<String>> {
        return api.getFacturasNro()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getPrecios(): Single<ApiRequest<Precios>> {
        return api.getPrecios()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getProductos(): Single<ApiRequest<Productos>> {
        return api.getProductos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getProductoPrecio(cod:String, cantidad : Int ): Single<ApiRequest<Precios>> {
        return api.getProductoPrecio(cod,cantidad)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getCategorias(): Single<ApiRequest<Categorias>> {
        return api.getCategorias()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }
}