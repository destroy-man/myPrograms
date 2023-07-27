package ru.korobeynikov.p08subcomponentsobjectstransfer.other

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p08subcomponentsobjectstransfer.R
import ru.korobeynikov.p08subcomponentsobjectstransfer.databinding.ActivityMainBinding
import ru.korobeynikov.p08subcomponentsobjectstransfer.di.MainComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainComponentBuilder: MainComponent.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        (application as App).appComponent.injectMainActivity(this)
        mainComponentBuilder.activity(this).build()
    }
}