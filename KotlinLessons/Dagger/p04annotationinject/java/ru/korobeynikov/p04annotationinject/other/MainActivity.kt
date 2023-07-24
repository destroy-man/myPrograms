package ru.korobeynikov.p04annotationinject.other

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p04annotationinject.R
import ru.korobeynikov.p04annotationinject.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        (application as App).appComponent.injectMainActivity(this)
    }

    @Inject
    fun postInit(networkUtils: NetworkUtils) {
        Log.d("myLogs", "postInt")
    }
}