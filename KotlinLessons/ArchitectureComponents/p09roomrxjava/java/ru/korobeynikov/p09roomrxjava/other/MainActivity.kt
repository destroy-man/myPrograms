package ru.korobeynikov.p09roomrxjava.other

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.korobeynikov.p09roomrxjava.R
import ru.korobeynikov.p09roomrxjava.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val db = App.getInstance().getDatabase()
        db.employeeDao().getById(1).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onSuccess = { employee -> log("$employee") },
                onError = { throwable -> log("$throwable") },
                onComplete = { log("Employee not found") }
            )
    }

    private fun log(text: String) = Log.d("myLogs", text)
}