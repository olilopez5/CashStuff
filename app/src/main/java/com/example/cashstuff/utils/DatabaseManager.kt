package com.example.cashstuff.utils

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.example.cashstuff.data.Movement


class DatabaseManager (context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION
) {
    //busca db y si no existe lo crea OnCreate para inicializar
    // y si existe cambiar la version +1 (2) ejecuta onUpgrade

    companion object {
        const val DATABASE_NAME = "bank_movements.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_MOVEMENT =
            "CREATE TABLE ${Movement.TABLE_NAME} (" +
                    "${Movement.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Movement.COLUMN_NAME_AMOUNT} DOUBLE," +
                    "${Movement.COLUMN_NAME_DATE} TEXT)"


        private const val SQL_DROP_TABLE_MOVEMENT = "DROP TABLE IF EXISTS ${Movement.TABLE_NAME}"

    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.i("DATABASE", "Create table MOVEMENTS")
        db.execSQL(SQL_CREATE_TABLE_MOVEMENT)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDestroy(db)
        onCreate(db)
//        Log.w("DATABASE", "Upgrading database from version $oldVersion to $newVersion")
//
//        // Verifica si la versión actual es menor a la nueva versión
//        if (oldVersion < 2) {  // Nueva versión que incluye el campo de prioridad
//            // Añadir columna PRIORITY a la tabla de MOVEMENT
//            val alterTableSQL = "ALTER TABLE ${Movement.TABLE_NAME} ADD COLUMN ${Movement.COLUMN_NAME_PRIORITY}"
//            db.execSQL(alterTableSQL)
//            Log.i("DATABASE", "Added priority column to MOVEMENT table")
   //     }

        // Aquí puedes añadir más cambios si incrementas más versiones en el futuro.
    }

    fun onDestroy(db: SQLiteDatabase) {
        Log.w("DATABASE", "Drop table MOVEMENTS")
        db.execSQL(SQL_DROP_TABLE_MOVEMENT)
    }
}