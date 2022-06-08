package ru.korobeynikov.p1011contentprovider

import android.content.*
import android.database.Cursor
import android.net.Uri
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.text.TextUtils
import android.util.Log
import kotlin.IllegalArgumentException


class MyContactsProvider : ContentProvider() {

    val LOG_TAG="myLogs"

    companion object{
        val DB_NAME="mydb"
        val DB_VERSION=1
        val CONTACT_TABLE="contacts"
        val CONTACT_ID="_id"
        val CONTACT_NAME="name"
        val CONTACT_EMAIL="email"
        val DB_CREATE="create table $CONTACT_TABLE($CONTACT_ID integer primary key autoincrement, $CONTACT_NAME text" +
                ", $CONTACT_EMAIL text);"
        val AUTHORITY="ru.korobeynikov.providers.AddressBook"
        val CONTACT_PATH="contacts"
        val CONTACT_CONTENT_URI=Uri.parse("content://$AUTHORITY/$CONTACT_PATH")
        val CONTACT_CONTENT_TYPE="vnd.android.cursor.dir/vnd.$AUTHORITY.$CONTACT_PATH"
        val CONTACT_CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd.$AUTHORITY.$CONTACT_PATH"
        val URI_CONTACTS=1
        val URI_CONTACTS_ID=2
        val uriMatcher=UriMatcher(UriMatcher.NO_MATCH)
    }

    private lateinit var dbHelper:DBHelper
    lateinit var db:SQLiteDatabase

    override fun onCreate(): Boolean {
        uriMatcher.addURI(AUTHORITY,CONTACT_PATH,URI_CONTACTS)
        uriMatcher.addURI(AUTHORITY,"$CONTACT_PATH/#",URI_CONTACTS_ID)
        Log.d(LOG_TAG,"onCreate")
        dbHelper=DBHelper(context)
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?,
                       sortOrder: String?, ): Cursor? {
        var sortOrderMutable=sortOrder
        var selectionMutable=selection
        Log.d(LOG_TAG,"query, $uri")
        when(uriMatcher.match(uri)){
            URI_CONTACTS->{
                Log.d(LOG_TAG,"URI_CONTACTS")
                if(TextUtils.isEmpty(sortOrderMutable))
                    sortOrderMutable="$CONTACT_NAME ASC"
            }
            URI_CONTACTS_ID->{
                val id=uri.lastPathSegment
                Log.d(LOG_TAG,"URI_CONTACTS_ID, $id")
                if(TextUtils.isEmpty(selectionMutable))
                    selectionMutable="$CONTACT_ID = $id"
                else
                    selectionMutable+=" AND $CONTACT_ID = $id"
            }
            else->IllegalArgumentException("Wrong URI: $uri")
        }
        db=dbHelper.writableDatabase
        val cursor=db.query(CONTACT_TABLE,projection,selectionMutable,selectionArgs,null,null
            ,sortOrderMutable)
        cursor.setNotificationUri(context?.contentResolver,CONTACT_CONTENT_URI)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(LOG_TAG,"insert, $uri")
        if(uriMatcher.match(uri)!=URI_CONTACTS)
            IllegalArgumentException("Wrong URI: $uri")
        db=dbHelper.writableDatabase
        val rowID=db.insert(CONTACT_TABLE,null,values)
        val resultUri=ContentUris.withAppendedId(CONTACT_CONTENT_URI,rowID)
        context?.contentResolver?.notifyChange(resultUri,null)
        return resultUri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var selectionMutable=selection
        Log.d(LOG_TAG,"delete, $uri")
        when(uriMatcher.match(uri)){
            URI_CONTACTS->Log.d(LOG_TAG,"URI_CONTACTS")
            URI_CONTACTS_ID->{
                val id=uri.lastPathSegment
                Log.d(LOG_TAG,"URI_CONTACTS_ID, $id")
                if(TextUtils.isEmpty(selectionMutable))
                    selectionMutable="$CONTACT_ID = $id"
                else
                    selectionMutable+=" AND $CONTACT_ID = $id"
            }
            else->IllegalArgumentException("Wrong URI: $uri")
        }
        db=dbHelper.writableDatabase
        val cnt=db.delete(CONTACT_TABLE,selectionMutable,selectionArgs)
        context?.contentResolver?.notifyChange(uri,null)
        return cnt
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?, ): Int {
        var selectionMutable=selection
        Log.d(LOG_TAG,"update, $uri")
        when(uriMatcher.match(uri)){
            URI_CONTACTS->Log.d(LOG_TAG,"URI_CONTACTS")
            URI_CONTACTS_ID->{
                val id=uri.lastPathSegment
                Log.d(LOG_TAG,"URI_CONTACTS_ID, $id")
                if(TextUtils.isEmpty(selectionMutable))
                    selectionMutable="$CONTACT_ID = $id"
                else
                    selectionMutable+=" AND $CONTACT_ID = $id"
            }
            else->IllegalArgumentException("Wrong URI: $uri")
        }
        db=dbHelper.writableDatabase
        val cnt=db.update(CONTACT_TABLE,values,selectionMutable,selectionArgs)
        context?.contentResolver?.notifyChange(uri,null)
        return cnt
    }

    override fun getType(uri: Uri): String? {
        Log.d(LOG_TAG,"getType, $uri")
        when(uriMatcher.match(uri)){
            URI_CONTACTS->return CONTACT_CONTENT_TYPE
            URI_CONTACTS_ID->return CONTACT_CONTENT_ITEM_TYPE
        }
        return null
    }

    private class DBHelper(context:Context?):SQLiteOpenHelper(context,DB_NAME,null,DB_VERSION){

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(DB_CREATE)
            val cv=ContentValues()
            for(i in 1..3){
                cv.put(CONTACT_NAME,"name $i")
                cv.put(CONTACT_EMAIL,"email $i")
                db?.insert(CONTACT_TABLE,null,cv)
            }
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
    }
}