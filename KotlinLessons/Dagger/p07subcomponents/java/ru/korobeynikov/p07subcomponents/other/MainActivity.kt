package ru.korobeynikov.p07subcomponents.other

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p07subcomponents.R
import ru.korobeynikov.p07subcomponents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val mainComponent = (application as App).appComponent.getMainComponent()
        mainActivityPresenter = mainComponent.getMainActivityPresenter()
    }
}