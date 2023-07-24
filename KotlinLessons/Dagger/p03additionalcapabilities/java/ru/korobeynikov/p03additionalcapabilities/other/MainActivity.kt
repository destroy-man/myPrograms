package ru.korobeynikov.p03additionalcapabilities.other

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p03additionalcapabilities.R
import ru.korobeynikov.p03additionalcapabilities.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        (application as App).appComponent.getEventHandlers()
    }
}