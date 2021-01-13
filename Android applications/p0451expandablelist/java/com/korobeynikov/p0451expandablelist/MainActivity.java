package com.korobeynikov.p0451expandablelist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

public class MainActivity extends AppCompatActivity {

    String[] groups={"HTC","Samsung","LG"};
    String[] phonesHTC={"Sensation","Desire","Wildfire","Hero"};
    String[] phonesSams={"Galaxy S II","Galaxy Nexus","Wave"};
    String[] phonesLG={"Optimus","Optimus Link","Optimus Black","Optimus One"};
    ArrayList<Map<String,String>> groupData;
    ArrayList<Map<String,String>> childDataItem;
    ArrayList<ArrayList<Map<String,String>>> childData;
    Map<String,String> m;
    ExpandableListView elvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        groupData=new ArrayList<Map<String,String>>();
        for(String group:groups){
            m=new HashMap<String,String>();
            m.put("groupName",group);
            groupData.add(m);
        }
        String[] groupFrom={"groupName"};
        int[] groupTo={android.R.id.text1};
        childData=new ArrayList<ArrayList<Map<String,String>>>();

        childDataItem=new ArrayList<Map<String,String>>();
        for(String phone:phonesHTC){
            m=new HashMap<String,String>();
            m.put("phoneName",phone);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        childDataItem=new ArrayList<Map<String,String>>();
        for(String phone:phonesSams){
            m=new HashMap<String,String>();
            m.put("phoneName",phone);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        childDataItem=new ArrayList<Map<String,String>>();
        for(String phone:phonesLG){
            m=new HashMap<String,String>();
            m.put("phoneName",phone);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        String[] childFrom={"phoneName"};
        int[] childTo={android.R.id.text1};
        SimpleExpandableListAdapter adapter=new SimpleExpandableListAdapter(this, groupData, android.R.layout.simple_expandable_list_item_1,
                                                                            groupFrom, groupTo, childData, android.R.layout.simple_list_item_1,
                                                                            childFrom,childTo);
        elvMain=findViewById(R.id.elvMain);
        elvMain.setAdapter(adapter);
    }
}