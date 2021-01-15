package com.korobeynikov.p0481simpleadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends AppCompatActivity {

    final String ATTRIBUTE_NAME_TEXT="text";
    final String ATTRIBUTE_NAME_CHECKED="checked";
    final String ATTRIBUTE_NAME_IMAGE="image";
    ListView lvSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] texts={"sometext 1","sometext 2","sometext 3","sometext 4","sometext 5"};
        boolean[] checked={true,false,false,true,false};
        int img=R.drawable.ic_launcher_foreground;
        ArrayList<Map<String,Object>> data=new ArrayList<Map<String,Object>>(texts.length);
        Map<String,Object> m;
        for(int i=0;i<texts.length;i++){
            m=new HashMap<String,Object>();
            m.put(ATTRIBUTE_NAME_TEXT,texts[i]);
            m.put(ATTRIBUTE_NAME_CHECKED,checked[i]);
            m.put(ATTRIBUTE_NAME_IMAGE,img);
            data.add(m);
        }
        String[] from={ATTRIBUTE_NAME_TEXT,ATTRIBUTE_NAME_CHECKED,ATTRIBUTE_NAME_IMAGE,ATTRIBUTE_NAME_TEXT};
        int[] to={R.id.tvText,R.id.cbChecked,R.id.ivImg,R.id.cbChecked};
        SimpleAdapter sAdapter=new SimpleAdapter(this,data,R.layout.item,from,to);
        lvSimple=findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);
    }
}