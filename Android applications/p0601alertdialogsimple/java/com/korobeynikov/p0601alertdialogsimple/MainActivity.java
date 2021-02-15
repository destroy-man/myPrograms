package com.korobeynikov.p0601alertdialogsimple;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int DIALOG_EXIT=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View view){
        showDialog(DIALOG_EXIT);
    }

    public void onBackPressed(){
        showDialog(DIALOG_EXIT);
    }

    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_EXIT){
            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle(R.string.exit);
            adb.setMessage(R.string.save_data);
            adb.setIcon(android.R.drawable.ic_dialog_info);
            adb.setPositiveButton(R.string.yes,myClickListener);
            adb.setNegativeButton(R.string.no,myClickListener);
            adb.setNeutralButton(R.string.cancel,myClickListener);
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    OnClickListener myClickListener=new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which){
                case Dialog.BUTTON_POSITIVE:
                    saveData();
                    finish();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    finish();
                    break;
                case Dialog.BUTTON_NEUTRAL:
                    break;
            }
        }
    };

    void saveData(){
        Toast.makeText(this,R.string.saved,Toast.LENGTH_SHORT).show();
    }
}