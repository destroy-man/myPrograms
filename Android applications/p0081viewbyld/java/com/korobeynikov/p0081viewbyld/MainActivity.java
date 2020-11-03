package com.korobeynikov.p0081viewbyld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView myTextView=findViewById(R.id.myText);
        myTextView.setText("New text in TextView");
        Button myBtn=findViewById(R.id.myBtn);
        myBtn.setText("My button");
        myBtn.setEnabled(false);
        CheckBox myChb=findViewById(R.id.myChb);
        myChb.setChecked(true);
    }
}
