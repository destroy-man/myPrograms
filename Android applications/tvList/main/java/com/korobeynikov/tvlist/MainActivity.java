package com.korobeynikov.tvlist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private StringBuilder contentGroup;
    private StringBuilder contentTvProgram;
    private String idChannel;
    private List<String> listGroup;
    private String ipServer;
    private boolean isFailConnection=false;

    //Перевод пикселей в dp
    public int convertPxToDp(int margin){
        float dp = this.getResources().getDisplayMetrics().density;
        int totalMargin =(int)dp * margin;
        return totalMargin;
    }
    //Размещение каналов
    public void getChannels(Map<String,String> mapChannels, String channelsId){
        List<ImageButton> listBtnChannels=new ArrayList<ImageButton>();
        Drawable drawable=null;
        Set setChannels=mapChannels.entrySet();
        Iterator iterator=setChannels.iterator();
        LinearLayout contentChannelsTv=findViewById(R.id.contentChannelsTv);
        contentChannelsTv.removeAllViews();
        while(iterator.hasNext()){
            Map.Entry item= (Map.Entry)iterator.next();
            if(item.getKey().toString().contains(channelsId)){
                StringBuilder srcImage=new StringBuilder(item.getValue().toString().split(";")[1]);
                int index=srcImage.lastIndexOf("/");
                srcImage=new StringBuilder("ch_"+srcImage.toString().substring(index+1,srcImage.length()-4));
                int resourceId=this.getResources().getIdentifier(srcImage.toString(),"drawable",this.getPackageName());

                ImageButton btnChannel=new ImageButton(this);
                if(drawable==null)drawable=btnChannel.getBackground();
                btnChannel.setImageResource(resourceId);

                FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(convertPxToDp(-10),0,0,0);
                btnChannel.setLayoutParams(lp);

                btnChannel.setTag(item.getKey()+";"+item.getValue().toString().split(";")[0]);

                contentChannelsTv.addView(btnChannel);
                listBtnChannels.add(btnChannel);
            }
        }
        //Обработка нажатия на кнопки каналов
        final Drawable background=drawable;
        for(int i=0;i<listBtnChannels.size();i++){
            ImageButton btnChannel=listBtnChannels.get(i);
            int i1 = i;
            btnChannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnChannel.setBackgroundColor(Color.BLUE);
                    for(int j=0;j<listBtnChannels.size();j++)
                        if(i1!=j)
                            listBtnChannels.get(j).setBackground(background);
                    idChannel=btnChannel.getTag().toString().split(";")[0];
                    TextView textViewCurrentChannel=findViewById(R.id.textViewCurrentChannel);
                    textViewCurrentChannel.setText(""+btnChannel.getTag().toString().split(";")[1]);
                    getTvPrograms();
                }
            });
        }
        //
    }
    //Размещение передач
    public void getTvPrograms(){
        LinearLayout contentProgramsTv=findViewById(R.id.contentProgramsTv);
        contentProgramsTv.removeAllViews();
        new TvProgramConnection().execute();
        try {
            Thread.sleep(1000);
            Toast.makeText(this,"",Toast.LENGTH_LONG);
            JSONArray programTv = new JSONArray(contentTvProgram.toString());
            for (int i = 0; i < programTv.length(); i++) {
                JSONObject program = (JSONObject) programTv.get(i);

                TextView textViewProgram=new TextView(this);

                FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.setMargins(convertPxToDp(10),0,0,0);
                textViewProgram.setLayoutParams(lp);
                textViewProgram.setGravity(Gravity.CENTER);

                textViewProgram.setText(""+program.get("name"));
                contentProgramsTv.addView(textViewProgram);
            }
        }
        catch(JSONException | InterruptedException ex){
            Toast.makeText(this, "Получены некорректные json данные", Toast.LENGTH_LONG);
        }
    }
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent intent=getIntent();
        ipServer=intent.getStringExtra("ipServer");

        new GroupConnection().execute();
        while(contentGroup==null)
            if(isFailConnection)break;
        if(!isFailConnection) {
            List<Button> listBtnGroup = new ArrayList<Button>();
            listGroup = new ArrayList<String>();
            Map<String, String> mapChannels = new LinkedHashMap<String, String>();
            try {
                Toast.makeText(this, "", Toast.LENGTH_LONG);
                JSONArray groupTv = new JSONArray(contentGroup.toString());
                for (int i = 0; i < groupTv.length(); i++) {
                    JSONObject group = (JSONObject) groupTv.get(i);
                    JSONArray channelsTv = group.getJSONArray("channels");
                    for (int j = 0; j < channelsTv.length(); j++) {
                        JSONObject channel = (JSONObject) channelsTv.get(j);
                        mapChannels.put(channel.getString("id"), channel.getString("name") + ";" + channel.getString("logoUrl"));
                        if (j == 0)
                            listGroup.add(group.get("id") + ";" + group.get("name") + ";" + channel.getString("id"));
                    }
                }
            } catch (JSONException ex) {
                Toast.makeText(this, "Получены некорректные json данные", Toast.LENGTH_LONG);
            }

            //Размещение ленты групп
            LinearLayout contentGroupTv = findViewById(R.id.contentGroupTv);
            for (int i = 0; i < listGroup.size(); i++) {
                Button btnGroup = new Button(this);

                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(convertPxToDp(-10), 0, 0, 0);
                btnGroup.setLayoutParams(lp);

                if (i > 0) btnGroup.setAlpha(0.5f);
                btnGroup.setText(listGroup.get(i).split(";")[1]);
                contentGroupTv.addView(btnGroup);

                listBtnGroup.add(btnGroup);
            }
            //Стартовое размещение ленты каналов
            TextView textViewCurrentChannel = findViewById(R.id.textViewCurrentChannel);
            textViewCurrentChannel.setText("Канал 0-0");
            getChannels(mapChannels, "channel-id-0-");
            //Стартовое размещение телепередач
            idChannel = "channel-id-0-0";
            getTvPrograms();
            //Обработка нажатия на кнопки группы
            for (int i = 0; i < listBtnGroup.size(); i++) {
                Button btnGroup = listBtnGroup.get(i);
                int i1 = i;
                btnGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnGroup.setAlpha(1);
                        for (int j = 0; j < listBtnGroup.size(); j++)
                            if (i1 != j)
                                listBtnGroup.get(j).setAlpha(0.5f);
                        int index = listGroup.get(i1).split(";")[2].lastIndexOf("-") + 1;
                        getChannels(mapChannels, listGroup.get(i1).split(";")[2].substring(0, index));
                    }
                });
            }
            //
        }
        else
            Toast.makeText(this,"Не удалось подключиться к серверу",Toast.LENGTH_LONG).show();
    }

    class GroupConnection extends AsyncTask< String, Void, String > {
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            URL url= null;
            BufferedReader reader=null;
            HttpURLConnection c=null;
            try {
                url = new URL("http://"+ipServer+":3000/tv/group/");
                c=(HttpURLConnection)url.openConnection();
                c.setRequestMethod("GET");
                c.setReadTimeout(10000);
                c.setConnectTimeout(5000);
                c.connect();
                reader= new BufferedReader(new InputStreamReader(c.getInputStream()));
                contentGroup=new StringBuilder();
                String line=null;
                while ((line=reader.readLine()) != null) {
                    contentGroup.append(line + "\n");
                }
                reader.close();
            } catch (Exception e) {
                isFailConnection=true;
            }
            finally{
                if(c!=null)
                    c.disconnect();
            }
            return null;
        }
    }

    class TvProgramConnection extends AsyncTask< String, Void, String > {
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            URL url= null;
            BufferedReader reader=null;
            HttpURLConnection c=null;
            try {
                url = new URL("http://"+ipServer+":3000/tv/program/:"+idChannel);
                c=(HttpURLConnection)url.openConnection();
                c.setRequestMethod("GET");
                c.setReadTimeout(10000);
                c.setConnectTimeout(5000);
                c.connect();
                reader= new BufferedReader(new InputStreamReader(c.getInputStream()));
                contentTvProgram=new StringBuilder();
                String line=null;
                while ((line=reader.readLine()) != null) {
                    contentTvProgram.append(line + "\n");
                }
                reader.close();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this,"Не удалось подключиться к каналу",Toast.LENGTH_LONG).show();
            }
            finally{
                if(c!=null)
                    c.disconnect();
            }
            return null;
        }
    }
}