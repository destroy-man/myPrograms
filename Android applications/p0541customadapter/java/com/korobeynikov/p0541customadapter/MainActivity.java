package com.korobeynikov.p0541customadapter;

import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ArrayList<Product> products=new ArrayList<Product>();
    BoxAdapter boxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillData();
        boxAdapter=new BoxAdapter(this,products);
        ListView lvMain=findViewById(R.id.lvMain);
        lvMain.setAdapter(boxAdapter);
    }

    void fillData(){
        for(int i=1;i<=20;i++)
            products.add(new Product("Product "+i,i*1000,R.drawable.ic_launcher_foreground,false));
    }

    public void showResult(View v){
        String result="Товары в корзине:";
        for(Product p:boxAdapter.getBox()){
            if(p.box)
                result+="\n"+p.name;
        }
        Toast.makeText(this,result,Toast.LENGTH_LONG).show();
    }
}