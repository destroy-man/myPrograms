package com.korobeynikov.p0341simplesqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    val LOG_TAG="myLogs"
    lateinit var dbHelper:DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener(this)
        btnRead.setOnClickListener(this)
        btnClear.setOnClickListener(this)
        btnUpd.setOnClickListener(this)
        btnDel.setOnClickListener(this)
        dbHelper=DBHelper(this)
    }

    override fun onClick(v: View?) {
        val cv=ContentValues()
        val name=""+etName.text
        val email=""+etEmail.text
        val id=""+etID.text
        val db=dbHelper.writableDatabase
        when(v?.id){
            R.id.btnAdd->{
                Log.d(LOG_TAG,"--- Insert in mytable: ---")
                cv.put("name",name)
                cv.put("email",email)
                val rowID=db.insert("mytable",null,cv)
                Log.d(LOG_TAG,"row inserted, ID = "+rowID)
            }
            R.id.btnRead->{
                Log.d(LOG_TAG,"--- Rows in mytable: ---")
                val c=db.query("mytable",null,null,null,null,null,null)
                if(c.moveToFirst()){
                    val idColINdex=c.getColumnIndex("id")
                    val nameColIndex=c.getColumnIndex("name")
                    val emailColIndex=c.getColumnIndex("email")
                    do{
                        Log.d(LOG_TAG,"ID = "+c.getInt(idColINdex)+", name = "+c.getString(nameColIndex)
                                +", email = "+c.getString(emailColIndex))
                    }while(c.moveToNext())
                }
                else
                    Log.d(LOG_TAG,"0 rows")
                c.close()
            }
            R.id.btnClear->{
                Log.d(LOG_TAG,"--- Clear mytable: ---")
                val clearCount=db.delete("mytable",null,null)
                Log.d(LOG_TAG,"deleted rows count = "+clearCount)
            }
            R.id.btnUpd->{
                if(!id.equals("",true)){
                    Log.d(LOG_TAG,"--- Update mytable: ---")
                    cv.put("name",name)
                    cv.put("email",email)
                    val updCount=db.update("mytable",cv,"id = ?",arrayOf(id))
                    Log.d(LOG_TAG,"updated rows count = "+updCount)
                }
            }
            R.id.btnDel->{
                if(!id.equals("",true)){
                    Log.d(LOG_TAG,"--- Delete from mytable: ---")
                    val delCount=db.delete("mytable","id = "+id,null)
                    Log.d(LOG_TAG,"deleted rows count = "+delCount)
                }
            }
        }
        dbHelper.close()
    }

    inner class DBHelper:SQLiteOpenHelper{

        constructor(context:Context):super(context,"myDB",null,1)

        override fun onCreate(db: SQLiteDatabase?) {
            Log.d(LOG_TAG,"--- onCreate database ---")
            db?.execSQL("create table mytable (id integer primary key autoincrement,name text,email text);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    }
}