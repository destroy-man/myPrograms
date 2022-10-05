package ru.korobeynikov.p09roomrxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = App.getInstance().getDatabase()
        db.employeeDao().getById(1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { employee ->
                    Log.d("myLogs", "$employee")
                },
                onError = {
                    Log.d("myLogs", "Ошибка!")
                },
                onComplete = {
                    Log.d("myLogs", "Не найдено!")
                }
            )
    }
}