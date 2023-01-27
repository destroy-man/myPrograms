package ru.korobeynikov.p0361sqlitequery

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0361sqlitequery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val logTag = "myLogs"
    private val name = arrayOf("Китай", "США", "Бразилия", "Россия", "Япония", "Германия",
        "Египет", "Италия", "Франция", "Канада")
    private val people = intArrayOf(1400, 311, 195, 142, 128, 82, 80, 60, 66, 35)
    private val region = arrayOf("Азия", "Америка", "Америка", "Европа", "Азия", "Европа",
        "Африка", "Европа", "Европа", "Америка")
    private lateinit var btnAll: Button
    private lateinit var etFunc: EditText
    private lateinit var etPeople: EditText
    private lateinit var etRegionPeople: EditText
    private lateinit var rgSort: RadioGroup
    private lateinit var dbHelper: DBHelper
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        btnAll = binding.btnAll
        binding.view = this
        etFunc = binding.etFunc
        etPeople = binding.etPeople
        etRegionPeople = binding.etRegionPeople
        rgSort = binding.rgSort
        dbHelper = DBHelper(this)
        db = dbHelper.writableDatabase
        val c = db.query("mytable", null, null, null, null,
            null, null)
        if (c.count == 0) {
            val cv = ContentValues()
            for (i in 0 until 10) {
                cv.put("name", name[i])
                cv.put("people", people[i])
                cv.put("region", region[i])
                Log.d(logTag, "id = ${db.insert("mytable", null, cv)}")
            }
        }
        c.close()
        dbHelper.close()
        clickButton(btnAll)
    }

    @SuppressLint("Range")
    fun clickButton(v: View) {
        db = dbHelper.writableDatabase
        var c: Cursor? = null
        when (v.id) {
            R.id.btnAll -> {
                Log.d(logTag, "--- Все записи ---")
                c = db.query("mytable", null, null, null,
                    null, null, null)
            }
            R.id.btnFunc -> {
                val sFunc = etFunc.text.toString()
                Log.d(logTag, "--- Функция $sFunc ---")
                val columns = arrayOf(sFunc)
                c = db.query("mytable", columns, null, null, null,
                    null, null)
            }
            R.id.btnPeople -> {
                val sPeople = etPeople.text.toString()
                Log.d(logTag, "--- Население больше $sPeople ---")
                val selection = "people > ?"
                val selectionArgs = arrayOf(sPeople)
                c = db.query("mytable", null, selection, selectionArgs, null,
                    null, null)
            }
            R.id.btnGroup -> {
                Log.d(logTag, "--- Население по региону ---")
                val columns = arrayOf("region", "sum(people) as people")
                val groupBy = "region"
                c = db.query("mytable", columns, null, null, groupBy,
                    null, null)
            }
            R.id.btnHaving -> {
                val sRegionPeople = etRegionPeople.text.toString()
                Log.d(logTag, "--- Регионы с населением больше $sRegionPeople ---")
                val columns = arrayOf("region", "sum(people) as people")
                val groupBy = "region"
                val having = "sum(people) > $sRegionPeople"
                c = db.query("mytable", columns, null, null, groupBy, having, null)
            }
            R.id.btnSort -> {
                var orderBy: String? = null
                when (rgSort.checkedRadioButtonId) {
                    R.id.rName -> {
                        Log.d(logTag, "--- Сортировка по наименованию ---")
                        orderBy = "name"
                    }
                    R.id.rPeople -> {
                        Log.d(logTag, "--- Сортировка по населению ---")
                        orderBy = "people"
                    }
                    R.id.rRegion -> {
                        Log.d(logTag, "--- Сортировка по региону ---")
                        orderBy = "region"
                    }
                }
                c = db.query("mytable", null, null, null,
                    null, null, orderBy)
            }
        }
        if (c != null) {
            if (c.moveToFirst())
                do {
                    val str = StringBuilder()
                    for (cn in c.columnNames)
                        str.append("$cn = ${c.getString(c.getColumnIndex(cn))}; ")
                    Log.d(logTag, str.toString())
                } while (c.moveToNext())
            c.close()
        } else
            Log.d(logTag, "Cursor is null")
        dbHelper.close()
    }

    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, "myDB", null, 1) {

        override fun onCreate(db: SQLiteDatabase?) {
            Log.d(logTag, "--- onCreate database ---")
            db?.execSQL("create table mytable (id integer primary key autoincrement,name text," +
                    "people integer,region text);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
    }
}