package com.example.palermo.network

class ApiRequest<T>(var status: Int = 0,
                 var mensaje: String = "",
                 var total: Int = 0,
                 var page: Int = 0,
                 var records: Int = 0,
                 var model: T? = null,
                 var rows: List<T> = ArrayList())