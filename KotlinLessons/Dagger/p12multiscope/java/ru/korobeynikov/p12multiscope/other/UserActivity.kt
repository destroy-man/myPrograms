package ru.korobeynikov.p12multiscope.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p12multiscope.R
import ru.korobeynikov.p12multiscope.databinding.ActivityUserBinding
import ru.korobeynikov.p12multiscope.di.UserComponent

class UserActivity : AppCompatActivity() {

    private lateinit var userComponent: UserComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityUserBinding>(this, R.layout.activity_user)
        userComponent = (application as App).appComponent.getUserComponentFactory().create(this)
        Log.d(MainActivity.TAG, "uiHelper from user = ${userComponent.getUiHelper()}")
    }
}