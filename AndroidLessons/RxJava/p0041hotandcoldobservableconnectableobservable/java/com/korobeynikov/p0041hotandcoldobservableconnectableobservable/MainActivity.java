package com.korobeynikov.p0041hotandcoldobservableconnectableobservable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.observables.ConnectableObservable;

public class MainActivity extends AppCompatActivity {

    private long beginTime;
    private Subscription subscription1;
    private Subscription subscription2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beginTime=System.currentTimeMillis();

        final Observer<Long> observer1=new Observer<Long>() {
            @Override
            public void onCompleted() {
                log("observer1 onCompleted");
            }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onNext(Long aLong) {
                log("observer1 onNext value = "+aLong);
            }
        };
        final Observer<Long> observer2=new Observer<Long>() {
            @Override
            public void onCompleted() {
                log("observer2 onCompleted");
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Long aLong) {
                log("observer2 onNext value = "+aLong);
            }
        };
        final Observable<Long> observable=Observable.interval(1, TimeUnit.SECONDS).take(10).cache();
        getWindow().getDecorView().postDelayed(new Runnable(){
            @Override
            public void run() {
                log("observer1 subscribe");
                subscription1=observable.subscribe(observer1);
            }
        },1500);
        getWindow().getDecorView().postDelayed(new Runnable(){
            @Override
            public void run() {
                log("observer2 subscribe");
                subscription2=observable.subscribe(observer2);
            }
        },4000);
        getWindow().getDecorView().postDelayed(new Runnable(){
            @Override
            public void run() {
                log("observer1 unsubscribe");
                subscription1.unsubscribe();
            }
        },5500);
        getWindow().getDecorView().postDelayed(new Runnable(){
            @Override
            public void run() {
                log("observer2 unsubscribe");
                subscription2.unsubscribe();
            }
        },6500);
        getWindow().getDecorView().postDelayed(new Runnable(){
            @Override
            public void run() {
                log("observer1 subscribe");
                observable.subscribe(observer1);
            }
        },7500);
    }

    public void log(String text){
        Log.d("myLogs", System.currentTimeMillis()-beginTime+" "+text);
    }
}