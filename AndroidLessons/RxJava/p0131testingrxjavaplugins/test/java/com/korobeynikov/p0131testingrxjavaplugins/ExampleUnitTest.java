package com.korobeynikov.p0131testingrxjavaplugins;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.BaseTestConsumer;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        TestSubscriber<Integer> testSubscriber=new TestSubscriber<>();
        Flowable.just(1,2,3,4,5).subscribe(testSubscriber);

        TestObserver<Long> testObserver=new TestObserver<>();
        TestScheduler testScheduler=new TestScheduler();
        Observable.interval(1000,TimeUnit.MILLISECONDS,testScheduler).take(10).subscribe(testObserver);
        testScheduler.advanceTimeTo(4500,TimeUnit.MILLISECONDS);
        testObserver.assertValueCount(4);
        testScheduler.advanceTimeBy(2000,TimeUnit.MILLISECONDS);
        testObserver.assertValueCount(6);
    }
}