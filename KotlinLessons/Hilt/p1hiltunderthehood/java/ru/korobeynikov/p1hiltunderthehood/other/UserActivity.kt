package ru.korobeynikov.p1hiltunderthehood.other

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1hiltunderthehood.R
import ru.korobeynikov.p1hiltunderthehood.databinding.ActivityUserBinding
import javax.inject.Inject

class UserActivity : HiltUserActivity() {

    @Inject
    lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityUserBinding>(this, R.layout.activity_user)
        Log.d(MainActivity.TAG, "userActivity repository = $repository")
    }
}