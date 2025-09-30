package com.example.walletapp.data.dao

import com.example.walletapp.model.Wallet
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.walletapp.data.db.WalletBD

class WalletDAO (context: Context) {

    private val dbHelper = WalletBD(context)

    fun inserir(wallet: Wallet): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(WalletBD.COLUMN_TIPO, wallet.tipo)
            put(WalletBD.COLUMN_DESCRICAO, wallet.descricao)
            put(WalletBD.COLUMN_VALOR, wallet.valor)
            put(WalletBD.COLUMN_TIMESTAMP, wallet.timestamp)
        }

        return try {
            val result = db.insert(WalletBD.TABLE_WALLET, null, values)
            result
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        } finally {
            db.close()
        }
    }

    fun obterTodas(): List<Wallet> {
        val wallets = mutableListOf<Wallet>()
        val db = dbHelper.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.query(
                WalletBD.TABLE_WALLET,
                null,
                null,
                null,
                null,
                null,
                "${WalletBD.COLUMN_TIMESTAMP} DESC"
            )

            while (cursor.moveToNext()) {
                val wallet = criarWalletDoCursor(cursor)
                wallets.add(wallet)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            db.close()
        }

        return wallets
    }

    fun obterPorTipo(tipo: String): List<Wallet> {
        val wallets = mutableListOf<Wallet>()
        val db = dbHelper.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.query(
                WalletBD.TABLE_WALLET,
                null,
                "${WalletBD.COLUMN_TIPO} = ?",
                arrayOf(tipo),
                null,
                null,
                "$WalletBD.COLUMN_TIMESTAMP} DESC"
            )

            while (cursor.moveToNext()) {
                val wallet = criarWalletDoCursor(cursor)
                wallets.add(wallet)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            db.close()
        }

        return wallets
    }

    fun calcularSaldo(): Double {
        val todasTransacoes = obterTodas()
        return todasTransacoes.sumOf { wallet ->
            if (wallet.isCredito()) wallet.valor else -wallet.valor

        }
    }

    fun deletar(id: Long): Boolean {
        val db = dbHelper.writableDatabase
        return try {
            val deletedRows = db.delete(
                WalletBD.TABLE_WALLET,
                "${WalletBD.COLUMN_ID} = ?",
                arrayOf(id.toString())
            )
            deletedRows > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.close()
        }
    }

    private fun criarWalletDoCursor(cursor: Cursor): Wallet {
        return Wallet(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(WalletBD.COLUMN_ID)),
            tipo = cursor.getString(cursor.getColumnIndexOrThrow(WalletBD.COLUMN_TIPO)),
            descricao = cursor.getString(cursor.getColumnIndexOrThrow(WalletBD.COLUMN_DESCRICAO)),
            valor = cursor.getDouble(cursor.getColumnIndexOrThrow(WalletBD.COLUMN_VALOR)),
            timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(WalletBD.COLUMN_TIMESTAMP))
        )
    }
}