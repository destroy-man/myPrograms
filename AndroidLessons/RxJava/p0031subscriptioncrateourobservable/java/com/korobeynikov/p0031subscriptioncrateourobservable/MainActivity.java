package com.korobeynikov.p0031subscriptioncrateourobservable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private final String TAG="myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.OnSubscribe<Integer> onSubscribe=new Observable.OnSubscribe<Integer>(){
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i=0;i<10;i++){
                    if(subscriber.isUnsubscribed())
                        return;
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        };
        Observable<Integer> observable=Observable.create(onSubscribe).subscribeOn(Schedulers.io());
        Observer<Integer> observer=new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError: "+e);
            }

            @Override
            public void onNext(Integer i) {
                Log.d(TAG,"onNext: "+i);
            }
        };
        final Subscription subscription=observable.subscribe(observer);
        getWindow().getDecorView().postDelayed(new Runnable(){
            @Override
            public void run() {
                Log.d(TAG,"unsubscribe");
                subscription.unsubscribe();
            }
        },4500);
    }

    public void log(String text){
        Log.d("myLogs",text);
    }
}