package ru.korobeynikov.p2hiltbasics.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import ru.korobeynikov.p2hiltbasics.R
import ru.korobeynikov.p2hiltbasics.databinding.ActivityUserBinding
import ru.korobeynikov.p2hiltbasics.other.MainActivity
import javax.inject.Inject

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityUserBinding>(this, R.layout.activity_user)
        Log.d(MainActivity.TAG, "user repository = $repository")
    }
}