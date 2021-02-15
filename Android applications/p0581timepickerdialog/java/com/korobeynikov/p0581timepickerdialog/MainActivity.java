package com.korobeynikov.p0581timepickerdialog;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    int DIALOG_TIME=1;
    int myHour=14;
    int myMinute=35;
    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTime=findViewById(R.id.tvTime);
    }

    public void onclick(View view){
        showDialog(DIALOG_TIME);
    }

    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_TIME){
            TimePickerDialog tpd=new TimePickerDialog(this,myCallBack,myHour,myMinute,true);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    OnTimeSetListener myCallBack=new OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour=hourOfDay;
            myMinute=minute;
            tvTime.setText("Time is "+myHour+" hours "+minute+" minutes");
        }
    };
}