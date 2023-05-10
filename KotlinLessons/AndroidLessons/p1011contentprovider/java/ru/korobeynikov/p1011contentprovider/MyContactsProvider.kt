package ru.korobeynikov.p1011contentprovider

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.util.Log

class MyContactsProvider : ContentProvider() {

    companion object {
        const val DB_NAME = "mydb"
        const val DB_VERSION = 1
        const val CONTACT_TABLE = "contacts"
        const val CONTACT_ID = "_id"
        const val CONTACT_NAME = "name"
        const val CONTACT_EMAIL = "email"
        const val DB_CREATE = "create table $CONTACT_TABLE(" +
                "$CONTACT_ID integer primary key autoincrement, $CONTACT_NAME text, $CONTACT_EMAIL text);"
        const val AUTHORITY = "ru.korobeynikov.providers.AddressBook"
        const val CONTACT_PATH = "contacts"
        const val CONTACT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$CONTACT_PATH"
        const val CONTACT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$AUTHORITY.$CONTACT_PATH"
        const val URI_CONTACTS = 1
        const val URI_CONTACTS_ID = 2
        val contactContentUri: Uri = Uri.parse("content://$AUTHORITY/$CONTACT_PATH")
        private var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    }

    private val logTag = "myLogs"
    private lateinit var dbHelper: DBHelper
    private lateinit var db: SQLiteDatabase

    init {
        uriMatcher.addURI(AUTHORITY, CONTACT_PATH, URI_CONTACTS)
        uriMatcher.addURI(AUTHORITY, "$CONTACT_PATH/#", URI_CONTACTS_ID)
    }

    override fun onCreate(): Boolean {
        Log.d(logTag, "onCreate")
        dbHelper = DBHelper(context)
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        var sortOrderMutable = sortOrder
        var selectionMutable = selection
        Log.d(logTag, "query, $uri")
        when (uriMatcher.match(uri)) {
            URI_CONTACTS -> {
                Log.d(logTag, "URI_CONTACTS")
                if (sortOrderMutable.isNullOrEmpty())
                    sortOrderMutable = "$CONTACT_NAME ASC"
            }
            URI_CONTACTS_ID -> {
                val id = uri.lastPathSegment
                Log.d(logTag, "URI_CONTACTS_ID, $id")
                if (selectionMutable.isNullOrEmpty())
                    selectionMutable = "$CONTACT_ID = $id"
                else
                    selectionMutable += " AND $CONTACT_ID = $id"
            }
            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = dbHelper.writableDatabase
        val cursor = db.query(CONTACT_TABLE, projection, selectionMutable, selectionArgs,
            null, null, sortOrderMutable)
        cursor.setNotificationUri(context?.contentResolver, contactContentUri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        Log.d(logTag, "insert, $uri")
        if (uriMatcher.match(uri) != URI_CONTACTS)
            throw IllegalArgumentException("Wrong URI: $uri")
        db = dbHelper.writableDatabase
        val rowID = db.insert(CONTACT_TABLE, null, values)
        val resultUri = ContentUris.withAppendedId(contactContentUri, rowID)
        context?.contentResolver?.notifyChange(contactContentUri, null)
        return resultUri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var selectionMutable = selection
        Log.d(logTag, "delete, $uri")
        when (uriMatcher.match(uri)) {
            URI_CONTACTS -> Log.d(logTag, "URI_CONTACTS")
            URI_CONTACTS_ID -> {
                val id = uri.lastPathSegment
                Log.d(logTag, "URI_CONTACTS_ID, $id")
                if (selectionMutable.isNullOrEmpty())
                    selectionMutable = "$CONTACT_ID = $id"
                else
                    selectionMutable += " AND $CONTACT_ID = $id"
            }
            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = dbHelper.writableDatabase
        val cnt = db.delete(CONTACT_TABLE, selectionMutable, selectionArgs)
        context?.contentResolver?.notifyChange(uri, null)
        return cnt
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        var selectionMutable = selection
        Log.d(logTag, "update, $uri")
        when (uriMatcher.match(uri)) {
            URI_CONTACTS -> Log.d(logTag, "URI_CONTACTS")
            URI_CONTACTS_ID -> {
                val id = uri.lastPathSegment
                Log.d(logTag, "URI_CONTACTS_ID, $id")
                if (selectionMutable.isNullOrEmpty())
                    selectionMutable = "$CONTACT_ID = $id"
                else
                    selectionMutable += " AND $CONTACT_ID = $id"
            }
            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = dbHelper.writableDatabase
        val cnt = db.update(CONTACT_TABLE, values, selectionMutable, selectionArgs)
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

    class DBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(DB_CREATE)
            val cv = ContentValues()
            for (i in 1..3) {
                cv.put(CONTACT_NAME, "name $i")
                cv.put(CONTACT_EMAIL, "email $i")
                db?.insert(CONTACT_TABLE, null, cv)
            }
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
    }
}