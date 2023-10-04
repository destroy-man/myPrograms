package ru.korobeynikov.p07memoryleakswhatisit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p07memoryleakswhatisit.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private var data = ""
    private lateinit var bytes: ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityTestBinding>(this, R.layout.activity_test)
        bytes = ByteArray(1000 * 1000 * 10) { 1 }
        (applicationContext as App).repository.registerListener {
            data = it
        }
    }
}