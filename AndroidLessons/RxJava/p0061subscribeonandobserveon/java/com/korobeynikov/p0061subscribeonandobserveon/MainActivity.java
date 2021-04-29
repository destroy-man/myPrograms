package com.korobeynikov.p0061subscribeonandobserveon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private long beginTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beginTime=System.currentTimeMillis();

        final Observer<Integer> observer=new Observer<Integer>() {
            @Override
            public void onCompleted() {
                log("observer onCompleted");
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Integer value) {
                log("observer onNext: "+value);
            }
        };
        Observable.OnSubscribe onSubscribe=new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                log("call");
                for(int i=0;i<3;i++){
                    try{
                        TimeUnit.MILLISECONDS.sleep(100);
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        };
        Func1<Integer,Integer> func=new Func1<Integer,Integer>(){
            @Override
            public Integer call(Integer integer) {
                log("func "+integer);
                return integer*10;
            }
        };
        Observable<Integer> observable=Observable.create(onSubscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(func)
                .observeOn(AndroidSchedulers.mainThread());
        log("subscribe");
        observable.subscribe(observer);
        log("done");
    }

    public void log(String text){
        Log.d("myLogs", System.currentTimeMillis()-beginTime+" "+text+" ["+Thread.currentThread().getName()+"]");
    }
}