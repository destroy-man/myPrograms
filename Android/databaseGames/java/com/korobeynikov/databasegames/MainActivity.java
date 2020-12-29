package com.korobeynikov.databasegames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1;
    private static final int REQUEST_CODE_PERMISSION_READ_STORAGE = 2;

    private MainActivity mainActivity;
    private EditText nameGame;
    private EditText ratingGame;
    private EditText yearGame;
    private TextView showGamesText;
    private DBGames dbGames;
    private Spinner genreGame;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_WRITE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "List games");
                    if(!directory.exists())
                        directory.mkdirs();
                    File gamesFile = new File(directory.getPath() + "/games.txt");
                    try{
                        StringBuilder games=new StringBuilder();
                        readDB(games,null,null,null);
                        if(!games.toString().isEmpty()) {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(gamesFile));
                            for (String game : games.toString().split("\n")) {
                                writer.write(game);
                                writer.newLine();
                            }
                            writer.close();
                            Toast.makeText(mainActivity,"Cохранение в файл успешно завершено!",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(mainActivity,"В базе данных нет записей!",Toast.LENGTH_LONG).show();
                    }
                    catch(IOException ex){
                        Toast.makeText(mainActivity,"Произошла ошибка при сохранении в файл!",Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(mainActivity,"Разрешение на сохранение игр получить не удалось!",Toast.LENGTH_LONG).show();
                break;
            case REQUEST_CODE_PERMISSION_READ_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "List games");
                    File gamesFile = new File(directory.getPath() + "/games.txt");
                    try{
                        boolean haveGames=false;
                        BufferedReader reader=new BufferedReader(new FileReader(gamesFile));
                        String line;
                        while((line=reader.readLine())!=null){
                            ContentValues cv=new ContentValues();
                            SQLiteDatabase db=dbGames.getWritableDatabase();
                            if(!isGameExist(line.split(";")[0],line.split(";")[2],db)){
                                cv.put("name", line.split(";")[0]);
                                cv.put("rating", line.split(";")[1]);
                                cv.put("year", line.split(";")[2]);
                                cv.put("genre",line.split(";")[3]);
                                db.insert("Games", null, cv);
                                if (!haveGames) haveGames = true;
                            }
                            db.close();
                        }
                        if(haveGames)
                            Toast.makeText(mainActivity,"Игры из файла успешно загружены!",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(mainActivity,"В файле не обнаружено игр для загрузки!",Toast.LENGTH_SHORT).show();
                        reader.close();
                    }
                    catch(IOException ex){
                        Toast.makeText(mainActivity,"Произошла ошибка при загрузке из файла!",Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(mainActivity,"Разрешение на загрузку игр получить не удалось!",Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void showListGames(boolean isOrder){
        int numberGame=0;

        String nameGameText=nameGame.getText().toString();
        String ratingGameText=ratingGame.getText().toString();
        String yearGameText=yearGame.getText().toString();
        String selection=null;
        String[] selectionArgs=null;
        StringBuilder textSelect=new StringBuilder();
        List<String> args=new ArrayList<String>();
        if(!nameGameText.isEmpty()) {
            textSelect.append("name LIKE ? AND ");
            args.add("%"+nameGameText+"%");
        }
        if(!ratingGameText.isEmpty()) {
            textSelect.append("rating=? AND ");
            args.add(ratingGameText);
        }
        if(!yearGameText.isEmpty()) {
            textSelect.append("year=? AND ");
            args.add(yearGameText);
        }
        if(genreGame.getSelectedItemId()>0){
            textSelect.append("genre=?");
            args.add(""+genreGame.getSelectedItemId());
        }

        if(!textSelect.toString().isEmpty()) {
            if (textSelect.toString().substring(textSelect.toString().length() - 5).equals(" AND "))
                textSelect.setLength(textSelect.toString().length() - 5);
            selection=textSelect.toString();
            selectionArgs=new String[args.size()];
            for(int i=0;i<args.size();i++)
                selectionArgs[i]=args.get(i);
        }

        String order=null;
        if(isOrder)order="rating desc,genre asc,year desc,id desc";

        StringBuilder games=new StringBuilder();
        readDB(games,selection,selectionArgs,order);
        if(!games.toString().isEmpty()){
            StringBuilder resultGames = new StringBuilder();
            for (String game : games.toString().split("\n")) {
                numberGame++;
                resultGames.append(" " + numberGame + ". " + game.split(";")[0] + " (" + game.split(";")[2] + ") - " + game.split(";")[1] + ".\n");
            }
            showGamesText.setText(resultGames.toString());
        }
        else
            showGamesText.setText("");
    }

    public void readDB(StringBuilder games,String selection,String[] selectionArgs,String order){
        SQLiteDatabase db=dbGames.getWritableDatabase();
        Cursor c = db.query("Games", null, selection, selectionArgs, null, null, order);
        if (c.moveToFirst()) {
            int nameColIndex=c.getColumnIndex("name");
            int ratingColIndex=c.getColumnIndex("rating");
            int yearColIndex=c.getColumnIndex("year");
            int genreColIndex=c.getColumnIndex("genre");
            do {
                games.append(c.getString(nameColIndex)+";"+c.getInt(ratingColIndex)+";"+c.getInt(yearColIndex)+";"+c.getInt(genreColIndex)+"\n");
            } while (c.moveToNext());
        }
        else
            Toast.makeText(mainActivity,"Нет сохраненных игр!",Toast.LENGTH_LONG).show();
        c.close();
        db.close();
    }

    public boolean isGameExist(String nameGameText,String yearGameText,SQLiteDatabase db){
        String selection="name=? AND year=?";
        String[] selectionArgs={nameGameText,yearGameText};
        Cursor c = db.query("Games", null, selection, selectionArgs, null, null, null);
        if(c.moveToFirst()){
            Toast.makeText(mainActivity,"В базе уже есть данная игра!",Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Очистить поля");
        menu.add(0, 2, 0, "Удалить все игры");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==1){
            if(nameGame==null)nameGame=findViewById(R.id.nameGameText);
            if(ratingGame==null)ratingGame=findViewById(R.id.ratingGameText);
            if(yearGame==null)yearGame=findViewById(R.id.yearGameText);
            nameGame.setText("");
            ratingGame.setText("");
            yearGame.setText("");
            genreGame.setSelection(0);
        }
        if(item.getItemId()==2){
            //
            LayoutInflater li=LayoutInflater.from(mainActivity);
            View deleteAllGamesView=li.inflate(R.layout.delete_all_games,null);
            AlertDialog.Builder deleteAllGamesDialogBuilder=new AlertDialog.Builder(mainActivity);
            deleteAllGamesDialogBuilder.setView(deleteAllGamesView);
            deleteAllGamesDialogBuilder.setCancelable(false).setPositiveButton("Нет",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog,int id){
                    dialog.cancel();
                }
            }).setNegativeButton("Да", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SQLiteDatabase db=dbGames.getWritableDatabase();
                    int delCount=db.delete("Games",null,null);
                    if(delCount>0)
                        Toast.makeText(mainActivity,"Успешно удалены все игры!",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(mainActivity,"Нет сохраненных игр!",Toast.LENGTH_SHORT).show();
                    db.close();
                }
            });
            AlertDialog alertDialog=deleteAllGamesDialogBuilder.create();
            alertDialog.show();
            //
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] genres={"-","FPS","TPS","Slasher","Stealth","RTS","TBS","ARPG","CRPG","Arcade","Adventure","Other"};
        genreGame=findViewById(R.id.genreGameList);
        ArrayAdapter<String> adapterGenres=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,genres);
        adapterGenres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreGame.setAdapter(adapterGenres);

        mainActivity=this;
        nameGame=findViewById(R.id.nameGameText);
        ratingGame=findViewById(R.id.ratingGameText);
        yearGame=findViewById(R.id.yearGameText);
        //Добавить игру
        Button addGame=findViewById(R.id.addGame);
        addGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameGameText=nameGame.getText().toString();
                String ratingGameText=ratingGame.getText().toString();
                String yearGameText=yearGame.getText().toString();
                if(nameGameText.isEmpty() || ratingGameText.isEmpty() || yearGameText.isEmpty() || genreGame.getSelectedItemId()==0)
                    Toast.makeText(mainActivity,"Для добавления игры необходимо заполнить поля Название, Оценка, Год и указать Жанр!",Toast.LENGTH_LONG).show();
                else if(Integer.parseInt(ratingGameText)<1 || Integer.parseInt(ratingGameText)>10)
                    Toast.makeText(mainActivity,"Некорректно указана оценка. Оценка должна принимать значение в интервале от 1 до 10.",Toast.LENGTH_LONG).show();
                else{
                    ContentValues cv=new ContentValues();
                    SQLiteDatabase db=dbGames.getWritableDatabase();
                    if(!isGameExist(nameGameText,yearGameText,db)) {
                        cv.put("name", nameGameText);
                        cv.put("rating", ratingGameText);
                        cv.put("year", yearGameText);
                        cv.put("genre",""+genreGame.getSelectedItemId());
                        db.insert("Games", null, cv);
                        Toast.makeText(mainActivity, "Игра успешно добавлена!", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }
            }
        });
        //Изменить параметры игры
        Button changeGame=findViewById(R.id.changeGame);
        changeGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameGameText=nameGame.getText().toString();
                String ratingGameText=ratingGame.getText().toString();
                String yearGameText=yearGame.getText().toString();
                if(nameGameText.isEmpty())
                    Toast.makeText(mainActivity,"Для изменения данных об игре необходимо заполнить поле Название!",Toast.LENGTH_LONG).show();
                else{
                    boolean incorrectRating=false;
                    ContentValues cv=new ContentValues();
                    SQLiteDatabase db=dbGames.getWritableDatabase();
                    if(!ratingGameText.isEmpty()){
                        if (Integer.parseInt(ratingGameText) < 1 || Integer.parseInt(ratingGameText) > 10) {
                            Toast.makeText(mainActivity, "Некорректно указана оценка. Оценка должна принимать значение в интервале от 1 до 10.", Toast.LENGTH_LONG).show();
                            incorrectRating=true;
                        }
                        else
                            cv.put("rating", ratingGameText);
                    }
                    if(genreGame.getSelectedItemId()>0)
                        cv.put("genre",""+genreGame.getSelectedItemId());
                    if (!incorrectRating) {
                        int updCount=0;

                        if(yearGameText.isEmpty())updCount=db.update("Games",cv,"name=?",new String[]{nameGameText});
                        else updCount=db.update("Games",cv,"name=? AND year=?",new String[]{nameGameText,yearGameText});

                        if(updCount>0)
                            Toast.makeText(mainActivity,"Данные об игре успешно изменены!",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(mainActivity,"Данная игра не найдена!",Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }
            }
        });
        //Удаление игры
        Button deleteGame=findViewById(R.id.deleteGame);
        deleteGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameGameText=nameGame.getText().toString();
                String yearGameText=yearGame.getText().toString();
                SQLiteDatabase db=dbGames.getWritableDatabase();
                int delCount=0;
                if(nameGameText.isEmpty())
                    Toast.makeText(mainActivity,"Для удаления игры необходимо заполнить поле Название!",Toast.LENGTH_LONG).show();
                else{
                    if(yearGameText.isEmpty())delCount=db.delete("Games","name='"+nameGameText+"'",null);
                    else delCount=db.delete("Games","name='"+nameGameText+"' AND year='"+yearGameText+"'",null);

                    if(delCount>0)
                        Toast.makeText(mainActivity,"Игра успешно удалена!",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(mainActivity,"Данная игра не найдена!",Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });
        //Сохранить игры в файл
        Button saveGamesInFile=findViewById(R.id.saveGamesInFile);
        saveGamesInFile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(mainActivity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_PERMISSION_WRITE_STORAGE);
            }
        });
        //Загрузить игры из файла
        Button loadGamesFromFile=findViewById(R.id.loadGamesFromFile);
        loadGamesFromFile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(mainActivity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_PERMISSION_READ_STORAGE);
            }
        });
        //Показать исходный список игр
        Button showGames=findViewById(R.id.showGames);
        showGames.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showListGames(false);
            }
        });
        //Показать отсортированный список игр
        Button sortGames=findViewById(R.id.sortGames);
        sortGames.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showListGames(true);
            }
        });
        //
        showGamesText=findViewById(R.id.showGamesText);

        dbGames=new DBGames(this);
    }

}