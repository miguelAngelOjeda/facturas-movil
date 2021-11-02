package com.example.palermo.models


data class Precios(
        var id: Long = 0,
        var descripcion: String = "",
        var precio: Double = 0.0,
        var total: Double = 0.0,
        var cantidadMinima: Int = 0,
        var cantidadMaxima: Int = 0)