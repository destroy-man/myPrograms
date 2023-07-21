package ru.korobeynikov.p01daggerintroduction.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p01daggerintroduction.R
import ru.korobeynikov.p01daggerintroduction.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var databaseHelper: DatabaseHelper

    @Inject
    lateinit var networkUtils: NetworkUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        (application as App).appComponent.injectMainActivity(this)
    }
}