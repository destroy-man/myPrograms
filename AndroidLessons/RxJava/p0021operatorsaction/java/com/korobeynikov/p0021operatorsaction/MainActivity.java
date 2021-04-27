package com.korobeynikov.p0021operatorsaction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final String TAG="myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Func1<String,Integer> stringToInteger=new Func1<String,Integer>(){
            @Override
            public Integer call(String s) {
                return Integer.parseInt(s);
            }
        };

        Func1<String,Boolean> filterFiveOnly=new Func1<String, Boolean>(){
            @Override
            public Boolean call(String s) {
                return s.contains("5");
            }
        };

        Func2<Integer,String,String> zipIntWithString=new Func2<Integer,String,String>(){
            @Override
            public String call(Integer i, String s) {
                return s+": "+i;
            }
        };

        Func1<Integer,Boolean> isFive=new Func1<Integer,Boolean>(){
            @Override
            public Boolean call(Integer i) {
                return i==5;
            }
        };

        Func1<Integer,Boolean> lessThanTen=new Func1<Integer,Boolean>(){
            @Override
            public Boolean call(Integer i) {
                return i<10;
            }
        };

        Observable<String> observable=Observable.from(new String[]{"one","two","three"});
        Action1<String> action=new Action1<String>(){
            @Override
            public void call(String s) {
                Log.d(TAG,"onNext: "+s);
            }
        };
        observable.subscribe(action);
    }

    private int longAction(String text){
        log("longAction");
        try{
            TimeUnit.SECONDS.sleep(1);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        return Integer.parseInt(text);
    }

    class CallableLongAction implements Callable<Integer>{

        private final String data;

        public CallableLongAction(String data){
            this.data=data;
        }

        @Override
        public Integer call() throws Exception {
            return longAction(data);
        }
    }

    public void log(String text){
        Log.d("myLogs",text);
    }
}