package ru.korobeynikov.daggermultiplemodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.korobeynikov.daggermultiplemodule.databinding.ActivityTasksBinding
import ru.korobeynikov.data.Database
import javax.inject.Inject

class TasksActivity : AppCompatActivity() {

    @Inject
    lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (applicationContext as App).appComponent.injectTasksActivity(this)
    }
}