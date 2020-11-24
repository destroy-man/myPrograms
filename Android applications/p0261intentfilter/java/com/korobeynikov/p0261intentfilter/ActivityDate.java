package com.korobeynikov.p0261intentfilter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityDate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date);
        SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
        String date=sdf.format(new Date(System.currentTimeMillis()));
        TextView tvDate=findViewById(R.id.tvDate);
        tvDate.setText(date);
    }
}