package ru.korobeynikov.p0691parcelable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0691parcelable.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivitySecondBinding>(this, R.layout.activity_second)
        Log.d(MainActivity.LOG_TAG, "getParcelableExtra")
        val myObj = intent.getParcelableExtra<MyObject>(MyObject::class.simpleName)
        Log.d(MainActivity.LOG_TAG, "myObj: ${myObj?.s}, ${myObj?.i}")
    }
}