package com.korobeynikov.p1012contprovclient

import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SimpleCursorAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    val CONTACT_URI=Uri.parse("content://com.korobeynikov.providers.AddressBook/contacts")
    val CONTACT_NAME="name"
    val CONTACT_EMAIL="email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cursor=contentResolver.query(CONTACT_URI,null,null,null,null)
        startManagingCursor(cursor)
        val from=arrayOf("name","email")
        val to=arrayOf(android.R.id.text1,android.R.id.text2)
        val adapter=SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor,from,to.toIntArray())
        lvContact.adapter=adapter
    }

    fun onClickInsert(v:View){
        val cv=ContentValues()
        cv.put(CONTACT_NAME,"name 4")
        cv.put(CONTACT_EMAIL,"email 4")
        val newUri=contentResolver.insert(CONTACT_URI,cv)
        Log.d(LOG_TAG,"insert, result Uri: "+newUri.toString())
    }

    fun onClickUpdate(v:View){
        val cv=ContentValues()
        cv.put(CONTACT_NAME,"name 5")
        cv.put(CONTACT_EMAIL,"email 5")
        val uri=ContentUris.withAppendedId(CONTACT_URI,2)
        val cnt=contentResolver.update(uri,cv,null,null)
        Log.d(LOG_TAG,"update, count = "+cnt)
    }

    fun onClickDelete(v:View){
        val uri=ContentUris.withAppendedId(CONTACT_URI,3)
        val cnt=contentResolver.delete(uri,null,null)
        Log.d(LOG_TAG,"delete, count = "+cnt)
    }

    fun onCLickError(v:View){
        val uri=Uri.parse("content://com.korobeynikov.providers.AddressBook/phones")
        try{
            val cursor=contentResolver.query(uri,null,null,null,null)
        }
        catch(ex:Exception){
            Log.d(LOG_TAG,"Error: "+ex.javaClass+", "+ex.message)
        }
    }
}