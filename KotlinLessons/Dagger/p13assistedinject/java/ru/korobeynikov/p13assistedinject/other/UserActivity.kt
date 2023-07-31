package ru.korobeynikov.p13assistedinject.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p13assistedinject.R
import ru.korobeynikov.p13assistedinject.databinding.ActivityUserBinding
import ru.korobeynikov.p13assistedinject.di.UserComponent

class UserActivity : AppCompatActivity() {

    private lateinit var userComponent: UserComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityUserBinding>(this, R.layout.activity_user)
        userComponent = (application as App).appComponent.getUserComponentFactory().create(this)
        Log.d(MainActivity.TAG, "uiHelper from user = ${userComponent.getUiHelper()}")
    }
}