package ru.korobeynikov.p5hiltentrypoint.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import dagger.hilt.EntryPoints
import dagger.hilt.android.AndroidEntryPoint
import ru.korobeynikov.p5hiltentrypoint.R
import ru.korobeynikov.p5hiltentrypoint.databinding.ActivityMainBinding
import ru.korobeynikov.p5hiltentrypoint.di.DataEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val entryPoint = EntryPoints.get(this, DataEntryPoint::class.java)
        val myConnection = entryPoint.getMyConnection()
        entryPoint.injectMyConnection(myConnection)
        Log.d(logTag, "databaseHelper = ${myConnection.databaseHelper}")
    }
}