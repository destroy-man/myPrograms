package ru.korobeynikov.p06roomentity.database.employee

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    //primaryKeys = ["first_name", "last_name"], Составной первичный ключ
    tableName = "employees",
    indices = [
        Index("salary"), //индекс из одного поля
        Index(value = ["first_name", "last_name"], unique = true) //составной индекс
    ]
)
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    /*
    @ColumnInfo(
        typeAffinity = TEXT, указывает что тип поля текст (в данном случае также понадобится конвертер для преобразования числа в строку)
        index = true указывает что для поля создан индекс
    )
     */
    val salary: Int,
    @Embedded(prefix = "address")
    val address: Address
) {
    @Ignore
    lateinit var avatar: Bitmap
}
