package com.example.walletapp.data.db

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper

class WalletBD (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_NAME = "walletapp.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_WALLET = "wallet"
        const val COLUMN_ID = "id"
        const val COLUMN_TIPO = "tipo"
        const val COLUMN_DESCRICAO = "descricao"
        const val COLUMN_VALOR = "valor"
        const val COLUMN_TIMESTAMP = "timestamp"

        private const val CREATE_TABLE = """
            CREATE TABLE $TABLE_WALLET (
                    $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_TIPO TEXT NOT NULL,
                    $COLUMN_DESCRICAO TEXT NOT NULL,
                    $COLUMN_VALOR REAL NOT NULL,
                    $COLUMN_TIMESTAMP INTEGER NOT NULL
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?execSQL("DROP TABLE IF EXISTS $TABLE_WALLET")
        onCreate(db)
    }
}