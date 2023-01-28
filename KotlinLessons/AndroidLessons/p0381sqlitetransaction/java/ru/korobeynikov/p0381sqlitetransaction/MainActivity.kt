package ru.korobeynikov.p0381sqlitetransaction

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0381sqlitetransaction.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val logTag = "myLogs"
    private lateinit var dbh: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Log.d(logTag, "--- onCreate Activity ---")
        dbh = DBHelper(this)
        myActions()
    }

    private fun myActions() {
        val db = dbh.writableDatabase
        val db2 = dbh.writableDatabase
        Log.d(logTag, "db = db2 - ${db == db2}")
        Log.d(logTag, "db open - ${db.isOpen}, db2 open - ${db2.isOpen}")
        db2.close()
        Log.d(logTag, "db open - ${db.isOpen}, db2 open - ${db2.isOpen}")
    }

    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, "myDB", null, 1) {

        override fun onCreate(db: SQLiteDatabase?) {
            Log.d(logTag, "--- onCreate database ---")
            db?.execSQL("create table mytable (id integer primary key autoincrement,val text);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
    }
}