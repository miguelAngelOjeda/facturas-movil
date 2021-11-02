package com.example.palermo.models

import java.util.*


data class Facturas(
        var id: Long = 0,
        var codigo: String = "",
        var total: Long = 0,
        var fechaCreacion: Date = Calendar.getInstance().time)