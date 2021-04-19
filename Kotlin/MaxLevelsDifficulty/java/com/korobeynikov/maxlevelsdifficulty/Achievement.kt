package com.korobeynikov.maxlevelsdifficulty

import androidx.room.Entity
import androidx.room.PrimaryKey

//Класс Entity представляющий сущность таблицы в базе данных
@Entity
class Achievement {

    @PrimaryKey(autoGenerate=true)
    var id:Long?=null

    var idGame:Long?=null

    var nameGame:String?=null

    var nameAchievement:String?=null

    var percent:Double?=null
}