package com.korobeynikov.p0101backpressure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func0;
import rx.functions.Func2;
import rx.observables.SyncOnSubscribe;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private long beginTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beginTime=System.currentTimeMillis();

        Observable.range(1,10).subscribe(new Subscriber<Integer>() {
            @Override
            public void onStart(){
                super.onStart();
                request(1);
            }

            @Override
            public void onCompleted() {
                log("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log("onError "+e);
            }

            @Override
            public void onNext(Integer integer) {
                log("onNext "+integer);
                request(1);
            }
        });
    }

    public void log(String text){
        Log.d("myLogs",text);
    }
}