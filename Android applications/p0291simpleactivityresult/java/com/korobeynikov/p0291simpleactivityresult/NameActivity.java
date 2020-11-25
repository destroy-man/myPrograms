package com.korobeynikov.p0291simpleactivityresult;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NameActivity extends AppCompatActivity implements OnClickListener{

    EditText etName;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);
        etName=findViewById(R.id.etName);
        btnOk=findViewById(R.id.btnOK);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.putExtra("name",etName.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }
}