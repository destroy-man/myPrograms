package ru.korobeynikov.p11rxjava2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.korobeynikov.p11rxjava2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val generator = Generator()
        Flowable.create({ emitter ->
            generator.setCallback(object : GeneratorCallback {
                override fun generate(int: Int) {
                    emitter.onNext(int)
                }
            })
            emitter.setCancellable {
                generator.setCallback(null)
            }
        }, BackpressureStrategy.BUFFER).subscribeBy(
            onNext = { log("onNext $it") },
            onError = { log("onError $it") },
            onComplete = { log("onComplete") }
        )
    }

    private fun log(text: String) = Log.d("myLogs", text)
}