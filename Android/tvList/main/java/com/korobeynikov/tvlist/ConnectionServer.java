package com.korobeynikov.tvlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConnectionServer extends AppCompatActivity {
    private ConnectionServer active;
    private SharedPreferences mSettings;
    private static final String APP_PREFERENCES = "settings";
    private static final String APP_PREFERENCES_IPSERVER = "ipServer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_server);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        active=this;
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        final TextView textIpServer=findViewById(R.id.editTextIpServer);
        //Восстановление последнего введеного ip адреса
        if(mSettings.contains(APP_PREFERENCES_IPSERVER))
            textIpServer.setText(mSettings.getString(APP_PREFERENCES_IPSERVER,""));
        //Кнопка подключения
        Button buttonConnect=findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=mSettings.edit();
                editor.putString(APP_PREFERENCES_IPSERVER,textIpServer.getText().toString());
                editor.apply();

                Intent intent = new Intent(active,MainActivity.class);
                intent.putExtra("ipServer",textIpServer.getText().toString());
                startActivityForResult(intent,1);
            }
        });
        //
    }
}