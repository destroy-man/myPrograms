package ru.korobeynikov.p20roomautomigrationspec.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Staff")
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "full_name")
    val name: String
    //v4
    //val salary: Int
)