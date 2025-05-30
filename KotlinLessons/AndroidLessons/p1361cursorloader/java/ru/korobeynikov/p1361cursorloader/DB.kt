package ru.korobeynikov.p1361cursorloader

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(private val mCtx: Context) {

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

    private var mDBHelper: DBHelper? = null
    private var mDB: SQLiteDatabase? = null

    fun open() {
        mDBHelper = DBHelper(mCtx, DB_NAME, null, DB_VERSION)
        mDB = mDBHelper?.writableDatabase
    }

    fun close() = mDBHelper?.close()

    fun getAllData() =
        mDB?.query(DB_TABLE, null, null, null, null, null, null)

    fun addRec(txt: String, img: Int) {
        with(ContentValues()) {
            put(COLUMN_TXT, txt)
            put(COLUMN_IMG, img)
            mDB?.insert(DB_TABLE, null, this)
        }
    }

    fun delRec(id: Long) = mDB?.delete(DB_TABLE, "$COLUMN_ID = $id", null)

    private class DBHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?,
                           version: Int) : SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(DB_CREATE)
            with(ContentValues()) {
                for (i in 1 until 5) {
                    put(COLUMN_TXT, "sometext $i")
                    put(COLUMN_IMG, R.mipmap.ic_launcher)
                    db?.insert(DB_TABLE, null, this)
                }
            }
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
    }
}