package ru.korobeynikov.p01lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import ru.korobeynikov.p01lifecycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var myServer: MyServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        myServer = MyServer()
    }

    override fun onStart() {
        super.onStart()
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED))
            lifecycle.addObserver(myServer)
    }

    override fun onStop() {
        super.onStop()
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
            lifecycle.removeObserver(myServer)
    }
}