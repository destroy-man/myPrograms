package com.korobeynikov.p0081combiningoperators;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;

public class MainActivity extends AppCompatActivity {

    private long beginTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beginTime=System.currentTimeMillis();

        Observable<String> observable1=Observable.just("A","B","C","D","E");
        observable1.concatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just(s).delay(100,TimeUnit.MILLISECONDS);
            }
        })
        .subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                log("onCompleted");
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(String next) {
                log("onNext "+next);
            }
        });
    }

    public void log(String text){
        Log.d("myLogs", System.currentTimeMillis()-beginTime+" "+text);
    }
}