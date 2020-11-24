package com.korobeynikov.p0261intentfilter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityDateEx extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date);
        SimpleDateFormat sdf=new SimpleDateFormat("EEE, MMM d, yyyy");
        String date=sdf.format(new Date(System.currentTimeMillis()));
        TextView tvDate=findViewById(R.id.tvDate);
        tvDate.setText(date);
    }
}