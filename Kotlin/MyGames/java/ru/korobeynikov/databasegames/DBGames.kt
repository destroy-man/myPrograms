package ru.korobeynikov.databasegames

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBGames(private val context: Context) : SQLiteOpenHelper(context, "dbGames", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Games (id integer primary key autoincrement, name text, rating integer" +
                ", year integer, genre integer);")
        Toast.makeText(context, "База данных успешно создана!", Toast.LENGTH_LONG).show()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
}