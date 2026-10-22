package ru.korobeynikov.p08roomquery.database

import androidx.room.ColumnInfo

data class Name(
    @ColumnInfo("first_name")
    val firstName: String,
    @ColumnInfo("last_name")
    val lastName: String
)
