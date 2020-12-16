package com.korobeynikov.p0381sqlitetransaction;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG="myLogs";
    DBHelper dbh;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG,"--- onCreate activity ---");
        dbh=new DBHelper(this);
        myActions();
    }

    void myActions(){
        db = dbh.getWritableDatabase();
        SQLiteDatabase db2=dbh.getWritableDatabase();
        Log.d(LOG_TAG,"db = db2 - "+db.equals(db2));
        Log.d(LOG_TAG,"db open - "+db.isOpen()+", db2 open - "+db2.isOpen());
        db2.close();
        Log.d(LOG_TAG,"db open - "+db.isOpen()+", db2 open - "+db2.isOpen());
    }

    void insert(SQLiteDatabase db,String table,String value){
        Log.d(LOG_TAG,"Insert in table "+table+" value = "+value);
        ContentValues cv=new ContentValues();
        cv.put("val",value);
        db.insert("mytable",null,cv);
    }

    void read(SQLiteDatabase db,String table){
        Log.d(LOG_TAG,"Read table "+table);
        Cursor c=db.query("mytable",null,null,null,null,null,null);
        if(c!=null){
            Log.d(LOG_TAG,"Records count = "+c.getCount());
            if(c.moveToFirst()){
                do{
                    Log.d(LOG_TAG,c.getString(c.getColumnIndex("val")));
                }while(c.moveToNext());
            }
            c.close();
        }
    }

    void delete(SQLiteDatabase db,String table){
        Log.d(LOG_TAG,"Delete all from table "+table);
        db.delete("mytable",null,null);
    }

    class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context){
            super(context,"myDB",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG,"--- onCreate database ---");
            db.execSQL("create table mytable (id integer primary key autoincrement,val text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}