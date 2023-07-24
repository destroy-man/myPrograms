package ru.korobeynikov.p05objecttransmitmenttocomponent.other

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p05objecttransmitmenttocomponent.R
import ru.korobeynikov.p05objecttransmitmenttocomponent.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        (application as App).appComponent.injectMainActivity(this)
    }
}