package ru.korobeynikov.p0641alertdialogitemsmulti

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(private val mCtx: Context) {

    companion object {
        private const val DB_NAME = "mydb"
        private const val DB_VERSION = 1
        private const val DB_TABLE = "mytab"
        const val COLUMN_ID = "_id"
        const val COLUMN_CHK = "checked"
        const val COLUMN_TXT = "txt"
        private const val DB_CREATE =
            "create table $DB_TABLE($COLUMN_ID integer primary key, $COLUMN_CHK integer, $COLUMN_TXT text);"
    }

    private lateinit var mDBHelper: DBHelper
    private lateinit var mDB: SQLiteDatabase

    fun open() {
        mDBHelper = DBHelper(mCtx, DB_NAME, null, DB_VERSION)
        mDB = mDBHelper.writableDatabase
    }

    fun close() {
        mDBHelper.close()
    }

    fun getAllData(): Cursor =
        mDB.query(DB_TABLE, null, null, null, null, null, null)

    fun changeRec(pos: Int, isChecked: Boolean) {
        val cv = ContentValues()
        cv.put(COLUMN_CHK, if (isChecked) 1 else 0)
        mDB.update(DB_TABLE, cv, "$COLUMN_ID = ${pos + 1}", null)
    }

    private class DBHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?,
                           version: Int) : SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(DB_CREATE)
            val cv = ContentValues()
            for (i in 1 until 5) {
                cv.put(COLUMN_ID, i)
                cv.put(COLUMN_TXT, "sometext $i")
                cv.put(COLUMN_CHK, 0)
                db?.insert(DB_TABLE, null, cv)
            }
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
    }
}