package ru.korobeynikov.p13testingrxjavaplugins

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import ru.korobeynikov.p13testingrxjavaplugins.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        RxJavaPlugins.setOnObservableSubscribe { observable, observer ->
            log("Observer: $observer subscribe to Observable: $observable")
            observer
        }
        Observable.just("1", "2", "3").map { s ->
            s.toInt()
        }.subscribeBy { int ->
            log("onNext $int")
        }
    }

    private fun log(text: String) = Log.d("myLogs", text)
}