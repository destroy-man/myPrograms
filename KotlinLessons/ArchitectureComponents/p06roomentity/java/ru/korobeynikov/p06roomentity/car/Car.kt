package ru.korobeynikov.p06roomentity.car

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import ru.korobeynikov.p06roomentity.employee.Employee

@Entity(foreignKeys = [ForeignKey(entity = Employee::class, parentColumns = ["id"],
    childColumns = ["employee_id"], onDelete = CASCADE)])
data class Car(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val model: String,
    val year: Int,
    @ColumnInfo(name = "employee_id")
    val employeeID: Long
)
