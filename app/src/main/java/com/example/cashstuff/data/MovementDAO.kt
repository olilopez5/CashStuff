package com.example.cashstuff.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.cashstuff.utils.DatabaseManager

class MovementDAO(context: Context) {

    val databaseManager = DatabaseManager(context)

    fun insert(movement: Movement) {
        val db = databaseManager.writableDatabase
        val values = ContentValues().apply {
            put(Movement.COLUMN_NAME_AMOUNT, movement.amount)
            put(Movement.COLUMN_NAME_DATE, movement.date)

        }
        try {
            val newRowId = db.insert(Movement.TABLE_NAME, null, values)
            Log.i("DATABASE", "Insert : $newRowId")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun update(movement: Movement) {
        val db = databaseManager.writableDatabase
        val values = ContentValues().apply {
            put(Movement.COLUMN_NAME_AMOUNT, movement.amount)
            put(Movement.COLUMN_NAME_DATE, movement.date)

        }
        try {
            db.update(
                Movement.TABLE_NAME,
                values,
                "${Movement.COLUMN_NAME_ID} = ${movement.id}",
                null
            )
            Log.i("DATABASE", "Update movement: ${movement.id}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun delete(movement: Movement) {
        val db = databaseManager.writableDatabase
        try {
            db.delete(Movement.TABLE_NAME, "${Movement.COLUMN_NAME_ID} = ${movement.id}", null)
            Log.i("DATABASE", "Delete movement: ${movement.id}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun findById(id: Long): Movement? {
        val db = databaseManager.readableDatabase
        val projection = arrayOf(
            Movement.COLUMN_NAME_ID,
            Movement.COLUMN_NAME_AMOUNT,
            Movement.COLUMN_NAME_DATE

        )
        val selection = "${Movement.COLUMN_NAME_ID} = $id"
        var movement: Movement? = null
        try {
            val cursor = db.query(
                Movement.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null
            )
            if (cursor.moveToNext()) {
                movement = cursorToMovement(cursor)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return movement
    }

    fun findAll(): List<Movement> {
        val db = databaseManager.readableDatabase
        val movementList: MutableList<Movement> = mutableListOf()
        try {
            val cursor = db.query(
                Movement.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null

            )
            while (cursor.moveToNext()) {
                val movement = cursorToMovement(cursor)
                movementList.add(movement)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return movementList
    }


    fun sortedByDate(ascending: Boolean = true): List<Movement> {
        val db = databaseManager.readableDatabase
        val movementList: MutableList<Movement> = mutableListOf()
        val order = if (ascending) "ASC" else "DESC"
        try {
            val cursor = db.query(
                Movement.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                "${Movement.COLUMN_NAME_DATE} $order"
            )
            while (cursor.moveToNext()) {
                val movement = cursorToMovement(cursor)
                movementList.add(movement)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return movementList
    }

    private fun cursorToMovement(cursor: Cursor): Movement {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(Movement.COLUMN_NAME_ID))
        val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(Movement.COLUMN_NAME_AMOUNT))
        val date = cursor.getString(cursor.getColumnIndexOrThrow(Movement.COLUMN_NAME_DATE))

        return Movement(id, amount, date)
    }
}
