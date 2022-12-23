package ru.korobeynikov.p09espressoidlingresource

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class DataRepository {
    fun loadData(): Flowable<String> {
        return Flowable.create({ emitter ->
            TimeUnit.SECONDS.sleep(3)
            emitter.onNext("Data string")
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}