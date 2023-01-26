package ru.korobeynikov.p0351sqliteupdatedelete

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0351sqliteupdatedelete.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val logTag = "myLogs"
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etID: EditText
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        etName = binding.etName
        etEmail = binding.etEmail
        etID = binding.etID
        dbHelper = DBHelper(this)
    }

    fun clickButton(v: View) {
        val cv = ContentValues()
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val id = etID.text.toString()
        val db = dbHelper.writableDatabase
        when (v.id) {
            R.id.btnAdd -> {
                Log.d(logTag, "--- Insert in mytable: ---")
                cv.put("name", name)
                cv.put("email", email)
                val rowID = db.insert("mytable", null, cv)
                Log.d(logTag, "row inserted, ID = $rowID")
            }
            R.id.btnRead -> {
                Log.d(logTag, "--- Rows in mytable: ---")
                val c = db.query("mytable", null, null, null,
                    null, null, null)
                if (c.moveToFirst()) {
                    val idColIndex = c.getColumnIndex("id")
                    val nameColIndex = c.getColumnIndex("name")
                    val emailColIndex = c.getColumnIndex("email")
                    do {
                        Log.d(logTag, "ID = ${c.getInt(idColIndex)}, " +
                                "name = ${c.getString(nameColIndex)}, email = ${c.getString(emailColIndex)}")
                    } while (c.moveToNext())
                } else
                    Log.d(logTag, "0 rows")
                c.close()
            }
            R.id.btnClear -> {
                Log.d(logTag, "--- Clear mytable: ---")
                val clearCount = db.delete("mytable", null, null)
                Log.d(logTag, "deleted rows count = $clearCount")
            }
            R.id.btnUpd -> {
                if (!id.equals("", true)) {
                    Log.d(logTag, "--- Update mytable: ---")
                    cv.put("name", name)
                    cv.put("email", email)
                    val updCount = db.update("mytable", cv, "id = ?", arrayOf(id))
                    Log.d(logTag, "updated rows count = $updCount")
                }
            }
            R.id.btnDel -> {
                if (!id.equals("", true)) {
                    Log.d(logTag, "--- Delete from mytable: ---")
                    val delCount = db.delete("mytable", "id = $id", null)
                    Log.d(logTag, "deleted rows count = $delCount")
                }
            }
        }
        dbHelper.close()
    }

    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, "myDB", null, 1) {

        override fun onCreate(db: SQLiteDatabase?) {
            Log.d(logTag, "--- onCreate database ---")
            db?.execSQL("create table mytable (id integer primary key autoincrement,name text," +
                    "email text);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
    }
}