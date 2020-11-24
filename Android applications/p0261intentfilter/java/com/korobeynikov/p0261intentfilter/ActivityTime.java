package com.korobeynikov.p0261intentfilter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        String time=sdf.format(new Date(System.currentTimeMillis()));
        TextView tvTime=findViewById(R.id.tvTime);
        tvTime.setText(time);
    }
}