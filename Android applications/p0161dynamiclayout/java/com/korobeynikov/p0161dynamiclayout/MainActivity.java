package com.korobeynikov.p0161dynamiclayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linLayout=new LinearLayout(this);
        linLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linLayoutParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        setContentView(linLayout,linLayoutParam);
        LinearLayout.LayoutParams lpView=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tv=new TextView(this);
        tv.setText("TextView");
        tv.setLayoutParams(lpView);
        linLayout.addView(tv);
        Button btn=new Button(this);
        btn.setText("Button");
        linLayout.addView(btn,lpView);
        LinearLayout.LayoutParams leftMarginParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        leftMarginParams.leftMargin=50;
        Button btn1=new Button(this);
        btn1.setText("Button1");
        linLayout.addView(btn1,leftMarginParams);
        LinearLayout.LayoutParams rightGravityParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rightGravityParams.gravity= Gravity.RIGHT;
        Button btn2=new Button(this);
        btn2.setText("Button2");
        linLayout.addView(btn2,rightGravityParams);
    }
}