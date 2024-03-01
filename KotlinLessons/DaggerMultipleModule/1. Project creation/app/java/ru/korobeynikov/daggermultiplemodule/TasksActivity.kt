package ru.korobeynikov.daggermultiplemodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.korobeynikov.data.Database

class TasksActivity : AppCompatActivity() {

    val database = Database()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
    }
}