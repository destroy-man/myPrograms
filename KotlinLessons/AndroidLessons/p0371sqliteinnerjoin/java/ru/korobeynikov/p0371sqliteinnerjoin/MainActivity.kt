package ru.korobeynikov.p0371sqliteinnerjoin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    val position_id=arrayOf(1,2,3,4)
    val position_name=arrayOf("Директор","Программер","Бухгалтер","Охранник")
    val position_salary=arrayOf(15000,13000,10000,8000)
    val people_name=arrayOf("Иван","Марья","Петр","Антон","Даша","Борис","Костя","Игорь")
    val people_posid=arrayOf(2,3,2,2,3,1,2,4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbh=DBHelper(this)
        val db=dbh.writableDatabase
        var c:Cursor
        Log.d(LOG_TAG,"--- Table position ---")
        c=db.query("position",null,null,null,null,null,null)
        logCursor(c)
        c.close()
        Log.d(LOG_TAG,"--- ---")
        Log.d(LOG_TAG,"--- Table people ---")
        c=db.query("people",null,null,null,null,null,null)
        logCursor(c)
        c.close()
        Log.d(LOG_TAG,"--- ---")
        Log.d(LOG_TAG,"--- INNER JOIN with rawQuery ---")
        val sqlQuery="select PL.name as Name, PS.name as Position, salary as Salary from people as PL" +
                " inner join position as PS on PL.posid = PS.id where salary > ?"
        c=db.rawQuery(sqlQuery, arrayOf("12000"))
        logCursor(c)
        c.close()
        Log.d(LOG_TAG,"--- ---")
        Log.d(LOG_TAG,"--- INNER JOIN with query ---")
        val table="people as PL inner join position as PS on PL.posid = PS.id"
        val columns=arrayOf("PL.name as Name","PS.name as Position","salary as Salary")
        val selection="salary < ?"
        val selectionArgs=arrayOf("12000")
        c=db.query(table,columns,selection,selectionArgs,null,null,null)
        logCursor(c)
        c.close()
        Log.d(LOG_TAG,"--- ---")
        dbh.close()
    }

    @SuppressLint("Range")
    fun logCursor(c:Cursor){
        if(c!=null){
            if(c.moveToFirst()){
                var str:String
                do{
                    str=""
                    for(cn in c.columnNames)
                        str+=cn+" = "+c.getString(c.getColumnIndex(cn))+"; "
                    Log.d(LOG_TAG,str)
                }while(c.moveToNext())
            }
        }
        else
            Log.d(LOG_TAG,"Cursor is null")
    }

    inner class DBHelper:SQLiteOpenHelper{

        constructor(context:Context):super(context,"myDB",null,1)

        override fun onCreate(db: SQLiteDatabase?) {
            Log.d(LOG_TAG,"--- onCreate database ---")
            val cv=ContentValues()
            db?.execSQL("create table position(id integer primary key,name text,salary integer);")
            for(i in position_id.indices){
                cv.clear()
                cv.put("id",position_id[i])
                cv.put("name",position_name[i])
                cv.put("salary",position_salary[i])
                db?.insert("position",null,cv)
            }
            db?.execSQL("create table people (id integer primary key autoincrement,name text,posid integer);")
            for(i in people_name.indices){
                cv.clear()
                cv.put("name",people_name[i])
                cv.put("posid",people_posid[i])
                db?.insert("people",null,cv)
            }
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
    }
}