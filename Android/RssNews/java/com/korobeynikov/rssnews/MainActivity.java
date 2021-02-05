package com.korobeynikov.rssnews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.CookieSyncManager;
import android.widget.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import android.view.*;
import android.view.View;
import android.widget.*;
import android.content.*;

public class MainActivity extends AppCompatActivity {

    public static RssItem selectedRssItem = null;
    String feedUrl = "";
    ListView rssListView = null;
    ArrayList<RssItem> rssItems = new ArrayList<RssItem>();
    ArrayAdapter<RssItem> aa = null;

    String lang=null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        new PrivacyConnection().execute();
        // get textview from our layout.xml
        final TextView rssURLTV = (TextView) findViewById(R.id.rssURL);

        // get the listview from layout.xml
        rssListView = (ListView) findViewById(R.id.rssListView);
        // here we specify what to execute when individual list items clicked
        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //@Override
            public void onItemClick(AdapterView<?> av, View view, int index, long arg3) {
                selectedRssItem = rssItems.get(index);
                // we call the other activity that shows a single rss item in
                // one page
                Intent intent = new Intent("rembo.network.urss.displayRssItem");
                startActivity(intent);
            }
        });

        //adapters are used to populate list. they take a collection,
        //a view (in our example R.layout.list_item
        aa = new ArrayAdapter<RssItem>(this, R.layout.list_item, rssItems);
        //here we bind array adapter to the list
        rssListView.setAdapter(aa);
        feedUrl = rssURLTV.getText().toString();
        refressRssList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(lang.equals("Russia"))
            menu.add(0,1,0,"Политика конфиденциальности");
        else if(lang.equals("Ukraine"))
            menu.add(0,1,0,"Політика конфіденційності");
        else if(lang.equals("Israel"))
            menu.add(0,1,0,"מדיניות פרטיות");
        else if(lang.equals("Kazakhstan"))
            menu.add(0,1,0,"Құпиялылық саясаты");
        else
            menu.add(0,1,0,"Privacy Policy");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==1) {
            if (lang.equals("Russia"))
                selectedRssItem = new RssItem("Политика конфиденциальности", "", new Date(), "https://yandex.ru/legal/confidential/?lang=ru");
            else if (lang.equals("Ukraine"))
                selectedRssItem = new RssItem("Політика конфіденційності", "", new Date(), "https://yandex.ru/legal/confidential/?lang=uk");
            else if (lang.equals("Israel"))
                selectedRssItem = new RssItem("מדיניות פרטיות", "", new Date(), "https://yandex.ru/legal/confidential/?lang=he");
            else if (lang.equals("Kazakhstan"))
                selectedRssItem = new RssItem("Құпиялылық саясаты", "", new Date(), "https://yandex.ru/legal/confidential/?lang=kk");
            else
                selectedRssItem = new RssItem("Privacy Policy", "", new Date(), "https://yandex.ru/legal/confidential/?lang=en");
            Intent intent = new Intent("rembo.network.urss.displayRssItem");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    class PrivacyConnection extends AsyncTask< String, Void, String > {

        @Override
        protected String doInBackground(String... args) {
            URL url;
            HttpURLConnection conn=null;
            String nameCountry=null;
            try{
                url=new URL("http://ip-api.com/json");
                conn= (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(5000);
                conn.connect();
                BufferedReader reader=new BufferedReader((new InputStreamReader(conn.getInputStream())));
                StringBuilder jsonText=new StringBuilder();
                String line=null;
                while((line=reader.readLine())!=null)
                    jsonText.append(line+"\n");
                reader.close();
                lang=jsonText.toString().split(",")[1].split(":")[1].replaceAll("\"","");
            }
            catch(Exception e){

            }
            finally{
                if(conn!=null)
                    conn.disconnect();
            }
            return null;
        }
    }

    private void refressRssList() {
        ArrayList<RssItem> newItems = RssItem.getRssItems(feedUrl);

        rssItems.clear();
        rssItems.addAll(newItems);

        aa.notifyDataSetChanged();
    }
}