package ru.korobeynikov.p1011contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri

class MyContactsProvider : ContentProvider() {

    private val uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private lateinit var dbHelper: DBHelper

    val logTag = "myLogs"

    lateinit var db: SQLiteDatabase

    init {
        uriMatcher.addURI(AUTHORITY, CONTACT_PATH, URI_CONTACTS)
        uriMatcher.addURI(AUTHORITY, "$CONTACT_PATH/#", URI_CONTACTS_ID)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var selectionMut = selection
        Log.d(logTag, "delete, $uri")
        when (uriMatcher.match(uri)) {
            URI_CONTACTS -> Log.d(logTag, "URI_CONTACTS")
            URI_CONTACTS_ID -> {
                val id = uri.lastPathSegment
                Log.d(logTag, "URI_CONTACTS_ID, $id")
                if (selection.isNullOrEmpty()) {
                    selectionMut = "$CONTACT_ID = $id"
                } else {
                    selectionMut += " AND $CONTACT_ID = $id"
                }
            }

            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = dbHelper.writableDatabase
        val cnt = db.delete(CONTACT_TABLE, selectionMut, selectionArgs)
        context?.contentResolver?.notifyChange(uri, null)
        return cnt
    }

    override fun getType(uri: Uri): String? {
        Log.d(logTag, "getType, $uri")
        return when (uriMatcher.match(uri)) {
            URI_CONTACTS -> CONTACT_CONTENT_TYPE
            URI_CONTACTS_ID -> CONTACT_CONTENT_ITEM_TYPE
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        Log.d(logTag, "insert, $uri")
        if (uriMatcher.match(uri) != URI_CONTACTS) {
            throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = dbHelper.writableDatabase
        val rowID = db.insert(CONTACT_TABLE, null, values)
        val resultUri = ContentUris.withAppendedId(CONTACT_CONTENT_URI, rowID)
        context?.contentResolver?.notifyChange(resultUri, null)
        return resultUri
    }

    override fun onCreate(): Boolean {
        Log.d(logTag, "onCreate")
        dbHelper = DBHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor {
        var sortOrderMut = sortOrder
        var selectionMut = selection
        Log.d(logTag, "query, $uri")
        when (uriMatcher.match(uri)) {
            URI_CONTACTS -> {
                Log.d(logTag, "URI_CONTACTS")
                if (sortOrder.isNullOrEmpty()) {
                    sortOrderMut = "$CONTACT_NAME ASC"
                }
            }

            URI_CONTACTS_ID -> {
                val id = uri.lastPathSegment
                Log.d(logTag, "URI_CONTACTS_ID, $id")
                if (selection.isNullOrEmpty()) {
                    selectionMut = "$CONTACT_ID = $id"
                } else {
                    selectionMut += " AND $CONTACT_ID = $id"
                }
            }

            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = dbHelper.writableDatabase
        val cursor = db.query(
            CONTACT_TABLE,
            projection,
            selectionMut,
            selectionArgs,
            null,
            null,
            sortOrderMut
        )
        cursor.setNotificationUri(context?.contentResolver, CONTACT_CONTENT_URI)
        return cursor
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        var selectionMut = selection
        Log.d(logTag, "update, $uri")
        when (uriMatcher.match(uri)) {
            URI_CONTACTS -> Log.d(logTag, "URI_CONTACTS")
            URI_CONTACTS_ID -> {
                val id = uri.lastPathSegment
                Log.d(logTag, "URI_CONTACTS_ID, $id")
                if (selection.isNullOrEmpty()) {
                    selectionMut = "$CONTACT_ID = $id"
                } else {
                    selectionMut += " AND $CONTACT_ID = $id"
                }
            }

            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = dbHelper.writableDatabase
        val cnt = db.update(CONTACT_TABLE, values, selectionMut, selectionArgs)
        context?.contentResolver?.notifyChange(uri, null)
        return cnt
    }

    private class DBHelper(context: Context?) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.let {
                db.execSQL(DB_CREATE)
                val cv = ContentValues()
                for (i in 1..3) {
                    cv.put(CONTACT_NAME, "name $i")
                    cv.put(CONTACT_EMAIL, "email $i")
                    db.insert(CONTACT_TABLE, null, cv)
                }
            }
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
    }

    companion object {
        const val DB_NAME = "mydb"
        const val DB_VERSION = 1
        const val CONTACT_TABLE = "contacts"
        const val CONTACT_ID = "_id"
        const val CONTACT_NAME = "name"
        const val CONTACT_EMAIL = "email"

        const val DB_CREATE =
            "create table $CONTACT_TABLE($CONTACT_ID integer primary key autoincrement, " +
                    "$CONTACT_NAME text, $CONTACT_EMAIL text)"

        const val AUTHORITY = "ru.korobeynikov.providers.AddressBook"

        const val CONTACT_PATH = "contacts"
        const val CONTACT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$CONTACT_PATH"
        const val CONTACT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$AUTHORITY.$CONTACT_PATH"

        const val URI_CONTACTS = 1
        const val URI_CONTACTS_ID = 2

        val CONTACT_CONTENT_URI = "content://$AUTHORITY/$CONTACT_PATH".toUri()
    }
}