package ru.korobeynikov.p13assistedinject.other

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p13assistedinject.R
import ru.korobeynikov.p13assistedinject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "myLogs"
    }

    lateinit var serverApi: ServerApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        serverApi = (application as App).mainComponent.getMainActivityPresenter().serverApi
        Log.d(TAG, "${serverApi.host}:${serverApi.port}")
    }

    fun goToOrder() {
        startActivity(Intent(this, OrderActivity::class.java))
    }
}