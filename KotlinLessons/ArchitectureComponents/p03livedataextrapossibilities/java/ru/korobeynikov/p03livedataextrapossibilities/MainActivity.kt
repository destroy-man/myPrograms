package ru.korobeynikov.p03livedataextrapossibilities

import android.arch.lifecycle.LiveDataReactiveStreams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flowable: Flowable<String> = Flowable.create({}, BackpressureStrategy.BUFFER)
        LiveDataReactiveStreams.fromPublisher(flowable)
    }
}