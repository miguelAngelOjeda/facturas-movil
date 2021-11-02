package com.example.palermo.models

import java.util.*


data class Productos(
        var uuid: String = UUID.randomUUID().toString(),
        var id: Long = 0,
        var descripcion: String = "",
        var nombre: String = "",
        var codigo: String = "",
        var cantidad: Int? = 0,
        var precioUnitario: Double? = 0.0,
        var total: Double? = 0.0,
        var codCategoria: String? = null)