package com.korobeynikov.p0591datepickerdialog;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int DIALOG_DATE=1;
    int myYear=2011;
    int myMonth=2;
    int myDay=3;
    TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDate=findViewById(R.id.tvDate);
    }

    public void onclick(View view){
        showDialog(DIALOG_DATE);
    }

    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_DATE){
            DatePickerDialog dpd=new DatePickerDialog(this,myCallBack,myYear,myMonth,myDay);
            return dpd;
        }
        return super.onCreateDialog(id);
    }

    OnDateSetListener myCallBack=new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myYear=year;
            myMonth=month;
            myDay=dayOfMonth;
            tvDate.setText("Today is "+myDay+"/"+myMonth+"/"+myYear);
        }
    };
}