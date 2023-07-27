package ru.korobeynikov.p09componentsdependencies.other

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p09componentsdependencies.R
import ru.korobeynikov.p09componentsdependencies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val databaseHelper = (application as App).appComponent.getDatabaseHelper()
        val networkUtils = (application as App).appComponent.getNetworkUtils()
    }
}