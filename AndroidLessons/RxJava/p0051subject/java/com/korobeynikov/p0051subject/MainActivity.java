package com.korobeynikov.p0051subject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.subjects.SerializedSubject;
import rx.subjects.UnicastSubject;

public class MainActivity extends AppCompatActivity {

    private long beginTime;
    private Subscription subscription1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beginTime=System.currentTimeMillis();

        final PublishSubject<Long> subject=PublishSubject.create();
        final SerializedSubject<Long,Long> serializedSubject=new SerializedSubject<>(subject);
        final Action1<Long> action=new Action1<Long>() {

            private long sum=0;

            @Override
            public void call(Long aLong) {
                sum+=aLong;
            }

            @Override
            public String toString(){
                return "sum = "+sum;
            }
        };
        subject.subscribe(action);
        new Thread(){
            public void run(){
                super.run();
                for(int i=0;i<100000;i++)
                    serializedSubject.onNext(1L);
                log("first thread done");
            }
        }.start();
        new Thread(){
            public void run(){
                super.run();
                for(int i=0;i<100000;i++)
                    serializedSubject.onNext(1L);
                log("second thread done");
            }
        }.start();
        getWindow().getDecorView().postDelayed(new Runnable(){
            @Override
            public void run() {
                log(action.toString());
            }
        },2000);
    }

    public void log(String text){
        Log.d("myLogs", text);
    }
}