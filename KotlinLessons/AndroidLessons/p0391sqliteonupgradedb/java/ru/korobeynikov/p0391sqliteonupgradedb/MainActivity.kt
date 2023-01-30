package ru.korobeynikov.p0391sqliteonupgradedb

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
import ru.korobeynikov.p0391sqliteonupgradedb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val dbh = DBHelper(this)
        val db = dbh.writableDatabase
        Log.d(logTag, "--- Staff db v.${db.version} ---")
        writeStaff(db)
        dbh.close()
    }

    private fun writeStaff(db: SQLiteDatabase) {
        //Запрос данных по людям
        var c = db.rawQuery("select * from people", null)
        logCursor(c, "Table people")
        c.close()
        //Запрос данных по должностям
        c = db.rawQuery("select * from position", null)
        logCursor(c, "Table position")
        c.close()
        //Объединение данных по людям и должностям
        val sqlQuery = "select PL.name as Name, PS.name as Position, salary as Salary " +
                "from people as PL inner join position as PS on PL.posid = PS.id "
        c = db.rawQuery(sqlQuery, null)
        logCursor(c, "inner join")
        c.close()
    }

    @SuppressLint("Range")
    fun logCursor(c: Cursor?, title: String) {
        if (c != null)
            if (c.moveToFirst()) {
                Log.d(logTag, "$title. ${c.count} rows")
                do {
                    val sb = StringBuilder()
                    for (cn in c.columnNames)
                        sb.append("$cn = ${c.getString(c.getColumnIndex(cn))}; ")
                    Log.d(logTag, sb.toString())
                } while (c.moveToNext())
            } else
                Log.d(logTag, "$title. Cursor is null")
    }

    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, "staff", null, 2) {

        override fun onCreate(db: SQLiteDatabase?) {
            Log.d(logTag, "--- onCreate database ---")
            val peopleName = arrayOf("Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь")
            val peoplePosid = intArrayOf(2, 3, 2, 2, 3, 1, 2, 4)
            val positionId = intArrayOf(1, 2, 3, 4)
            val positionName = arrayOf("Директор", "Программер", "Бухгалтер", "Охранник")
            val positionSalary = intArrayOf(15000, 13000, 10000, 8000)
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

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            Log.d(logTag, "--- onUpgrade database from $oldVersion to $newVersion version --- ")
            if (oldVersion == 1 && newVersion == 2) {
                val cv = ContentValues()
                val positionId = intArrayOf(1, 2, 3, 4)
                val positionName = arrayOf("Директор", "Программер", "Бухгалтер", "Охранник")
                val positionSalary = intArrayOf(15000, 13000, 10000, 8000)
                db?.beginTransaction()
                try {
                    db?.execSQL("create table position (id integer primary key,name text," +
                            "salary integer);")
                    for (i in positionId.indices) {
                        cv.clear()
                        cv.put("id", positionId[i])
                        cv.put("name", positionName[i])
                        cv.put("salary", positionSalary[i])
                        db?.insert("position", null, cv)
                    }
                    db?.execSQL("alter table people add column posid integer;")
                    for (i in positionId.indices) {
                        cv.clear()
                        cv.put("posid", positionId[i])
                        db?.update("people", cv, "position = ?", arrayOf(positionName[i]))
                    }
                    db?.execSQL("create temporary table people_tmp (id integer,name text," +
                            "position text,posid integer);")
                    db?.execSQL("insert into people_tmp select id, name, position, posid from people;")
                    db?.execSQL("drop table people;")
                    db?.execSQL("create table people (id integer primary key autoincrement," +
                            "name text,posid integer);")
                    db?.execSQL("insert into people select id, name, posid from people_tmp;")
                    db?.execSQL("drop table people_tmp;")
                    db?.setTransactionSuccessful()
                } finally {
                    db?.endTransaction()
                }
            }
        }
    }
}