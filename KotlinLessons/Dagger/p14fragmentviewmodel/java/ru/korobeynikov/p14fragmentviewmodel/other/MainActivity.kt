package ru.korobeynikov.p14fragmentviewmodel.other

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ru.korobeynikov.p14fragmentviewmodel.R
import ru.korobeynikov.p14fragmentviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "myLogs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val mainComponent = (application as App).appComponent.getMainComponent()
        val viewModelFactory = mainComponent.getViewModelFactory()
        val model1 = ViewModelProvider(this, viewModelFactory)[ViewModel1::class.java]
        val model2 = ViewModelProvider(this, viewModelFactory)[ViewModel2::class.java]
        Log.d(LOG_TAG, "model1 = ${model1.hashCode()}, model2 = ${model2.hashCode()}")
    }
}