package com.example.cashstuff.data

data class Movement(
val id: Int = 0,
val amount: Double,
val date: String             // Store as ISO string: "2025-04-29"
) {
    companion object {
        const val TABLE_NAME = "Movements"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_AMOUNT = "amount"
        const val COLUMN_NAME_DATE = "date"



    }
}


