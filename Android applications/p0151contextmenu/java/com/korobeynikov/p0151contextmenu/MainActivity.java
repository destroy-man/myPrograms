package com.korobeynikov.p0151contextmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvColor,tvSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvColor=findViewById(R.id.tvColor);
        tvSize=findViewById(R.id.tvSize);
        registerForContextMenu(tvColor);
        registerForContextMenu(tvSize);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        switch(v.getId()){
            case R.id.tvColor:
                getMenuInflater().inflate(R.menu.color_menu,menu);
                break;
            case R.id.tvSize:
                getMenuInflater().inflate(R.menu.size_menu,menu);
                break;
        }
    }

    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle().equals("Red")) {
            tvColor.setTextColor(Color.RED);
            tvColor.setText("Text color = red");
        }
        else if(item.getTitle().equals("Green")) {
            tvColor.setTextColor(Color.GREEN);
            tvColor.setText("Text color = green");
        }
        else if(item.getTitle().equals("Blue")) {
            tvColor.setTextColor(Color.BLUE);
            tvColor.setText("Text color = blue");
        }
        else if(item.getTitle().equals("22")) {
            tvSize.setTextSize(22);
            tvSize.setText("Text size = 22");
        }
        else if(item.getTitle().equals("26")) {
            tvSize.setTextSize(26);
            tvSize.setText("Text size = 26");
        }
        else if(item.getTitle().equals("30")) {
            tvSize.setTextSize(30);
            tvSize.setText("Text size = 30");
        }
        return super.onContextItemSelected(item);
    }
}
