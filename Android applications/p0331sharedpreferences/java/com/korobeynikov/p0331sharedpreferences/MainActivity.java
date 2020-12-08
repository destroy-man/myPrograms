package com.korobeynikov.p0331sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    EditText etText;
    Button btnSave,btnLoad;
    SharedPreferences sPref;
    final String SAVED_TEXT="saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etText=findViewById(R.id.etText);
        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnLoad=findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);
        loadText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnSave:
                saveText();
                break;
            case R.id.btnLoad:
                loadText();
                break;
            default:
                break;
        }
    }

    void saveText(){
        sPref=getPreferences(MODE_PRIVATE);
        Editor ed=sPref.edit();
        ed.putString(SAVED_TEXT,etText.getText().toString());
        ed.commit();
        Toast.makeText(this,"Text saved",Toast.LENGTH_SHORT).show();
    }

    void loadText(){
        sPref=getPreferences(MODE_PRIVATE);
        String savedText=sPref.getString(SAVED_TEXT,"");
        etText.setText(savedText);
        Toast.makeText(this,"Text loaded",Toast.LENGTH_SHORT).show();
    }
}