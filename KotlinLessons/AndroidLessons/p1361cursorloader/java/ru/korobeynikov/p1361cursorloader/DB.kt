package ru.korobeynikov.p1361cursorloader

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(context: Context) {

    companion object {
        private const val DB_NAME = "mydb"
        private const val DB_VERSION = 1
        private const val DB_TABLE = "mytab"
        const val COLUMN_ID = "_id"
        const val COLUMN_IMG = "img"
        const val COLUMN_TXT = "txt"
        private const val DB_CREATE = "create table $DB_TABLE" +
                "($COLUMN_ID integer primary key autoincrement, $COLUMN_IMG integer, $COLUMN_TXT text);"
    }

    private val mCtx = context
    private lateinit var mDBHelper: DBHelper
    private lateinit var mDB: SQLiteDatabase

    fun open() {
        mDBHelper = DBHelper(mCtx, DB_NAME, null, DB_VERSION)
        mDB = mDBHelper.writableDatabase
    }

    fun close() {
        if (mDBHelper != null)
            mDBHelper.close()
    }

    fun getAllData(): Cursor {
        return mDB.query(DB_TABLE, null, null, null, null, null, null)
    }

    fun addRec(txt: String, img: Int) {
        val cv = ContentValues()
        cv.put(COLUMN_TXT, txt)
        cv.put(COLUMN_IMG, img)
        mDB.insert(DB_TABLE, null, cv)
    }

    fun delRec(id: Long) {
        mDB.delete(DB_TABLE, "$COLUMN_ID = $id", null)
    }

    private class DBHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?,
                           version: Int) : SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(DB_CREATE)
            val cv = ContentValues()
            for (i in 1 until 5) {
                cv.put(COLUMN_TXT, "sometext $i")
                cv.put(COLUMN_IMG, R.mipmap.ic_launcher)
                db?.insert(DB_TABLE, null, cv)
            }
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
    }
}