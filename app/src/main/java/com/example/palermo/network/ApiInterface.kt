package com.example.palermo.network

import com.example.palermo.models.Categorias
import com.example.palermo.models.Facturas
import com.example.palermo.models.Precios
import com.example.palermo.models.Productos
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    /*@POST("sps/solicitud/guardar")
    fun postSolicitudSPS(@Body solicitud: PreSolicitud): Single<SingleApiRequest<SolicitudResponse>>*/

    @GET("precios")
    fun getPrecios(
            @Query("all") todos: Boolean = false,
            @Query("page") page: Int = 0,
            @Query("rows") pageSize: Int = 100,
            @Query("_search") search: Boolean = false
    ): Single<ApiRequest<Precios>>

    @GET("facturas")
    fun getFacturas(
        @Query("all") todos: Boolean = true,
        @Query("page") page: Int = 1,
        @Query("rows") pageSize: Int = 100,
        @Query("_search") search: Boolean = false
    ): Single<ApiRequest<Facturas>>

    @GET("facturas/nro_factura")
    fun getFacturasNro(): Single<ApiRequest<String>>

    @GET("categorias")
    fun getCategorias(
            @Query("all") todos: Boolean = false,
            @Query("page") page: Int = 0,
            @Query("rows") pageSize: Int = 100,
            @Query("_search") search: Boolean = false
    ): Single<ApiRequest<Categorias>>

    @GET("productos")
    fun getProductos(
            @Query("all") todos: Boolean = true,
            @Query("page") page: Int = 0,
            @Query("rows") pageSize: Int = 1000,
            @Query("_search") search: Boolean = false
    ): Single<ApiRequest<Productos>>

    @GET("productos/precio")
    fun getProductoPrecio(
        @Query("codProd") codProd: String = "",
        @Query("cantidad") cantidad: Int = 0
    ): Single<ApiRequest<Precios>>
}