package com.korobeynikov.p0071errorhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<String> stringData=Observable.just("1","2","a","4","5");
        Observable<Long> observableMain=stringData.map(new Func1<String, Long>(){
            @Override
            public Long call(String s){
                return Long.parseLong(s);
            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observableErrors) {
                return observableErrors.zipWith(Observable.range(1, 3), new Func2<Throwable, Integer, Observable>() {
                    @Override
                    public Observable call(Throwable throwable, Integer integer) {
                        if(integer<3)
                            return Observable.just(0L);
                        else
                            return Observable.error(throwable);
                    }
                })
                .flatMap(new Func1<Observable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable observable) {
                        return observable;
                    }
                });
            }
        });
        observableMain.subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {
                log("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log("onError "+e);
            }

            @Override
            public void onNext(Long aLong) {
                log("onNext "+aLong);
            }
        });
    }

    public void log(String text){
        Log.d("myLogs", text);
    }
}