package com.korobeynikov.p0011basicsrxjavaobservableandobserver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Observer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<String> observable=Observable.from(new String[]{"one","two","three"});
        Observer<String> observer=new Observer<String>() {
            @Override
            public void onNext(String s) {
                log("onNext: "+s);
            }

            @Override
            public void onError(Throwable e) {
                log("onError: "+e);
            }

            @Override
            public void onCompleted() {
                log("onCompleted");
            }
        };
        observable.subscribe(observer);
    }

    public void log(String text){
        Log.d("myLogs",text);
    }
}