package ru.korobeynikov.p0371sqliteinnerjoin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0371sqliteinnerjoin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val logTag = "myLogs"
    val positionId = intArrayOf(1, 2, 3, 4)
    val positionName = arrayOf("Директор", "Программер", "Бухгалтер", "Охранник")
    val positionSalary = intArrayOf(15000, 13000, 10000, 8000)
    val peopleName = arrayOf("Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь")
    val peoplePosid = intArrayOf(2, 3, 2, 2, 3, 1, 2, 4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val dbh = DBHelper(this)
        val db = dbh.writableDatabase
        //Выводим в лог данные по должностям
        Log.d(logTag, "--- Table position ---")
        var c = db.query("position", null, null, null,
            null, null, null)
        logCursor(c)
        c.close()
        Log.d(logTag, "--- ---")
        //Выводим в лог данные по людям
        Log.d(logTag, "--- Table people ---")
        c = db?.query("people", null, null, null, null,
            null, null)
        logCursor(c)
        c.close()
        Log.d(logTag, "--- ---")
        //Выводим результат объединения используя rawQuery
        Log.d(logTag, "--- INNER JOIN with rawQuery ---")
        val sqlQuery = "select PL.name as Name, PS.name as Position, salary as Salary " +
                "from people as PL inner join position as PS on PL.posid = PS.id where salary > ?"
        c = db.rawQuery(sqlQuery, arrayOf("12000"))
        logCursor(c)
        c.close()
        Log.d(logTag, "--- ---")
        //Выводим результат объединения используя query и закрываем БД
        Log.d(logTag, "--- INNER JOIN with query ---")
        val table = "people as PL inner join position as PS on PL.posid = PS.id"
        val columns = arrayOf("PL.name as Name", "PS.name as Position", "salary as Salary")
        val selection = "salary < ?"
        val selectionArgs = arrayOf("12000")
        c = db.query(table, columns, selection, selectionArgs, null, null, null)
        logCursor(c)
        c.close()
        Log.d(logTag, "--- ---")
        dbh.close()
    }

    @SuppressLint("Range")
    fun logCursor(c: Cursor?) {
        if (c != null) {
            if (c.moveToFirst())
                do {
                    val str = StringBuilder()
                    for (cn in c.columnNames)
                        str.append("$cn = ${c.getString(c.getColumnIndex(cn))}; ")
                    Log.d(logTag, str.toString())
                } while (c.moveToNext())
            else
                Log.d(logTag, "Cursor is null")
        }
    }

    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, "myDB", null, 1) {

        override fun onCreate(db: SQLiteDatabase?) {
            Log.d(logTag, "--- onCreate database ---")
            val cv = ContentValues()
            db?.execSQL("create table position (id integer primary key,name text,salary integer);")
            for (i in positionId.indices) {
                cv.clear()
                cv.put("id", positionId[i])
                cv.put("name", positionName[i])
                cv.put("salary", positionSalary[i])
                db?.insert("position", null, cv)
            }
            db?.execSQL("create table people (id integer primary key autoincrement,name text," +
                    "posid integer);")
            for (i in peopleName.indices) {
                cv.clear()
                cv.put("name", peopleName[i])
                cv.put("posid", peoplePosid[i])
                db?.insert("people", null, cv)
            }
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
    }
}