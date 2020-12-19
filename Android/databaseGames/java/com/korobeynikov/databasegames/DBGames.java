package com.korobeynikov.databasegames;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DBGames extends SQLiteOpenHelper {

    MainActivity mainActivity;

    public DBGames(Context context){
        super(context,"dbGames",null,1);
        mainActivity=(MainActivity)context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Games (id integer primary key autoincrement, name text, rating integer, year integer);");
        Toast.makeText(mainActivity,"База данных успешно создана!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
