package com.korobeynikov.rssnews;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get textview from our layout.xml
        final TextView rssURLTV = (TextView) findViewById(R.id.rssURL);

        // get the listview from layout.xml
        rssListView = (ListView) findViewById(R.id.rssListView);
        // here we specify what to execute when individual list items clicked
        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //@Override
            public void onItemClick(AdapterView<?> av, View view, int index,
                                    long arg3) {
                selectedRssItem = rssItems.get(index);

                // we call the other activity that shows a single rss item in
                // one page
                Intent intent = new Intent(
                        "rembo.network.urss.displayRssItem");
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

    private void refressRssList() {

        ArrayList<RssItem> newItems = RssItem.getRssItems(feedUrl);

        rssItems.clear();
        rssItems.addAll(newItems);

        //TextView TVtitle=(TextView)findViewById(R.id.label);
        //CharSequence cs="0";
        //if(newItems.size()>0) cs="is 1";
        //if(newItems.size()>5) cs="is 5";
        ///TVtitle.setText(cs);

        aa.notifyDataSetChanged();
    }
}