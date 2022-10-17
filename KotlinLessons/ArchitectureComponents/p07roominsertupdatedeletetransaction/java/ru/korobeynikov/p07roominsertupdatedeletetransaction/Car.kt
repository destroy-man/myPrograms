package ru.korobeynikov.p07roominsertupdatedeletetransaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Employee::class, parentColumns = ["id"],
    childColumns = ["employee_id"], onDelete = CASCADE)])
data class Car(
    val model: String,
    val year: Int,
    @ColumnInfo(name = "employee_id")
    val employeeId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)