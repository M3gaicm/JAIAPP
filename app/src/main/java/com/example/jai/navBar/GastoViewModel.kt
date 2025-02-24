package com.example.jai.navBar

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf

data class Gasto(
    val nombreViaje: String,
    val creador: String,
    val participantes: List<String>,
    val coste: String
)

class GastoViewModel : ViewModel() {
    var gastos = mutableStateListOf<Gasto>()

    fun agregarGasto(gasto: Gasto) {
        gastos.add(gasto)
    }

    fun eliminarGasto(gasto: Gasto) {
        gastos.remove(gasto)
    }
}
