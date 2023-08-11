package ru.korobeynikov.p05subject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.PublishSubject
import ru.korobeynikov.p05subject.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val subject = PublishSubject.create<Long>().toSerialized()
        val consumer = object : Consumer<Long> {

            private var sum = 0L

            override fun accept(aLong: Long) {
                sum += aLong
            }

            override fun toString(): String {
                return "sum = $sum"
            }
        }
        subject.subscribe(consumer)
        Thread {
            for (i in 0 until 100000)
                subject.onNext(1L)
            log("first thread done")
        }.start()
        Thread {
            for (i in 0 until 100000)
                subject.onNext(1L)
            log("second thread done")
        }.start()
        Thread {
            TimeUnit.MILLISECONDS.sleep(2000)
            log(consumer.toString())
        }.start()
    }

    private fun log(text: String) = Log.d("myLogs", text)
}