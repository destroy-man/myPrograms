package ru.korobeynikov.p02dependenciesgraph.other

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p02dependenciesgraph.R
import ru.korobeynikov.p02dependenciesgraph.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        mainActivityPresenter = (application as App).appComponent.getMainActivityPresenter()
    }
}