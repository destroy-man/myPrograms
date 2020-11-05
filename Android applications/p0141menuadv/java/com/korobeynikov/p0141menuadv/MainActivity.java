package com.korobeynikov.p0141menuadv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    CheckBox chb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.textView);
        chb=findViewById(R.id.chbExtMenu);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        menu.setGroupVisible(1,chb.isChecked());
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        StringBuilder sb=new StringBuilder();
        sb.append("Item Menu");
        sb.append("\r\n groupId: "+String.valueOf(item.getGroupId()));
        sb.append("\r\n itemId: "+String.valueOf(item.getItemId()));
        sb.append("\r\n order: "+String.valueOf(item.getOrder()));
        sb.append("\r\n title: "+item.getTitle());
        tv.setText(sb.toString());
        return super.onOptionsItemSelected(item);
    }
}
