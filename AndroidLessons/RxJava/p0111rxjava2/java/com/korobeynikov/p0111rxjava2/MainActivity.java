package com.korobeynikov.p0111rxjava2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableMaybeObserver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Flowable<Integer> flowable=Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                for(int i=1;i<=20;i++){
                    try{
                        TimeUnit.MILLISECONDS.sleep(100);
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                        log("interrupted "+e);
                        return;
                    }
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        flowable.subscribe(new FlowableSubscriber<Integer>() {
            @Override
            public void onSubscribe(@NonNull Subscription s) {
                log("onSubscribe "+s);
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log("onNext "+integer);
            }

            @Override
            public void onError(Throwable t) {
                log("onError "+t);
            }

            @Override
            public void onComplete() {
                log("onComplete");
            }
        });
    }

    public void log(String text){
        Log.d("myLogs",text);
    }
}