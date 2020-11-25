package com.korobeynikov.p0271getintentaction;

import java.sql.Date;
import java.text.SimpleDateFormat;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        Intent intent=getIntent();
        String action=intent.getAction();
        String format="",textInfo="";
        if(action.equals("com.korobeynikov.intent.action.showTime")){
            format="HH:mm:ss";
            textInfo="Time: ";
        }
        else if(action.equals("com.korobeynikov.intent.action.showDate")){
            format="dd.MM.yyyy";
            textInfo="Date: ";
        }
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        String dateTime=sdf.format(new Date(System.currentTimeMillis()));
        TextView tvDate=findViewById(R.id.tvInfo);
        tvDate.setText(textInfo+dateTime);
    }
}