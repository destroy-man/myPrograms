package ru.korobeynikov.p13testingrxjavaplugins

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class RxTest {

    private lateinit var testObserver: TestObserver<Long>
    private lateinit var testScheduler: TestScheduler

    @Before
    fun prepareObjects() {
        testObserver = TestObserver<Long>()
        testScheduler = TestScheduler()
        Observable.interval(1000, TimeUnit.MILLISECONDS, testScheduler).take(10)
            .subscribe(testObserver)
    }

    @Test
    fun test() {
        testScheduler.advanceTimeTo(4500, TimeUnit.MILLISECONDS)
        testObserver.assertValueCount(4)
        testScheduler.advanceTimeBy(2000, TimeUnit.MILLISECONDS)
        testObserver.assertValueCount(6)
    }
}