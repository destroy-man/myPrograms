package ru.korobeynikov.myachievements.database

import androidx.room.Entity
import androidx.room.PrimaryKey

//Класс Entity представляющий сущность таблицы в базе данных
@Entity
class Achievement {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    var idGame: Long = 0L

    var nameGame: String? = null

    var nameAchievement: String? = null

    var percent: Double? = null

    var dateTime: Long? = null
}