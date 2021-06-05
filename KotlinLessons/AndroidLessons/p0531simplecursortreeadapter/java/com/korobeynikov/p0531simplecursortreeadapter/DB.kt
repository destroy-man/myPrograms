package com.korobeynikov.p0531simplecursortreeadapter

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(ctx:Context){

    companion object{
        private val DB_NAME="mydb"
        private val DB_VERSION=1
        private val COMPANY_TABLE="company"
        val COMPANY_COLUMN_ID="_id"
        val COMPANY_COLUMN_NAME="name"
        private val COMPANY_TABLE_CREATE="create table "+COMPANY_TABLE+"("+COMPANY_COLUMN_ID+" integer primary key, "+
                COMPANY_COLUMN_NAME+" text);"
        private val PHONE_TABLE="phone"
        val PHONE_COLUMN_ID="_id"
        val PHONE_COLUMN_NAME="name"
        val PHONE_COLUMN_COMPANY="company"
        private val PHONE_TABLE_CREATE="create table "+PHONE_TABLE+"("+PHONE_COLUMN_ID+" integer primary key autoincrement, "+
                PHONE_COLUMN_NAME+" text, "+PHONE_COLUMN_COMPANY+" integer);"
    }

    private lateinit var mDBHelper:DBHelper
    private lateinit var mDB:SQLiteDatabase
    private val mCtx=ctx

    fun open(){
        mDBHelper=DBHelper(mCtx,DB_NAME,null,DB_VERSION)
        mDB=mDBHelper.writableDatabase
    }

    fun close(){
        if(mDBHelper!=null)
            mDBHelper.close()
    }

    fun getCompanyData():Cursor{
        return mDB.query(COMPANY_TABLE,null,null,null,null,null,null)
    }

    fun getPhoneData(companyID:Long):Cursor{
        return mDB.query(PHONE_TABLE,null, PHONE_COLUMN_COMPANY+" = "+companyID,null,null,null,null)
    }

    class DBHelper:SQLiteOpenHelper{

        constructor(context:Context,name:String,factory: SQLiteDatabase.CursorFactory?,version:Int):
                super(context,name,factory,version)

        override fun onCreate(db: SQLiteDatabase?) {
            val cv=ContentValues()
            val companies=arrayOf("HTC","Samsung","LG")
            db?.execSQL(COMPANY_TABLE_CREATE)
            for(i in companies.indices){
                cv.put(COMPANY_COLUMN_ID,i+1)
                cv.put(COMPANY_COLUMN_NAME,companies[i])
                db?.insert(COMPANY_TABLE,null,cv)
            }
            val phonesHTC=arrayOf("Sensation","Desire","Wildfire","Hero")
            val phonesSams=arrayOf("Galaxy S II","Galaxy Nexus","Wave")
            val phonesLG=arrayOf("Optimus","Optimus Link","Optimus Black","Optimus One")
            db?.execSQL(PHONE_TABLE_CREATE)
            cv.clear()
            for(i in phonesHTC.indices){
                cv.put(PHONE_COLUMN_COMPANY,1)
                cv.put(PHONE_COLUMN_NAME,phonesHTC[i])
                db?.insert(PHONE_TABLE,null,cv)
            }
            for(i in phonesSams.indices){
                cv.put(PHONE_COLUMN_COMPANY,2)
                cv.put(PHONE_COLUMN_NAME,phonesSams[i])
                db?.insert(PHONE_TABLE,null,cv)
            }
            for(i in phonesLG.indices){
                cv.put(PHONE_COLUMN_COMPANY,3)
                cv.put(PHONE_COLUMN_NAME,phonesLG[i])
                db?.insert(PHONE_TABLE,null,cv)
            }
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    }

}