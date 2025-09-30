package com.example.walletapp.model

class Wallet(
    val id: Long = 0,
    val tipo: String,
    val descricao: String,
    val valor: Double,
    val timestamp: Long = System.currentTimeMillis()

){
    fun isCredito(): Boolean = tipo == "CREDITO"

    fun isDebito(): Boolean = tipo == "DEBITO"

    companion object {
        const val TIPO_CREDITO = "CREDITO"
        const val TIPO_DEBITO = "DEBITO"
    }
}