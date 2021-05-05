package com.korobeynikov.p0121rxbinding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView);

        Consumer<? super Boolean> consumer=RxView.visibility(textView);
        Observable.interval(300, TimeUnit.MILLISECONDS).take(50).map(new Function<Long, Boolean>() {
            @Override
            public Boolean apply(@NonNull Long aLong) throws Exception {
                return aLong%2==0;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);
    }

    public void log(String text){
        Log.d("myLogs", text);
    }
}