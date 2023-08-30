package ru.korobeynikov.p09espressoidlingresource

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class DataRepository {
    fun loadData(): Observable<String> = Observable.create { emitter ->
        TimeUnit.SECONDS.sleep(3)
        emitter.onNext("Data string")
        emitter.onComplete()
    }
}