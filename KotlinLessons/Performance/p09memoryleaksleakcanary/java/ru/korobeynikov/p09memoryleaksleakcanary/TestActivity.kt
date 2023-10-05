package ru.korobeynikov.p09memoryleaksleakcanary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p09memoryleaksleakcanary.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private val listener = object : Repository.Listener {
        override fun onDataChanged(newData: String) {
            data = newData
        }
    }
    var data = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityTestBinding>(this, R.layout.activity_test)
        (applicationContext as App).getRepository().registerListener(listener)
    }
}