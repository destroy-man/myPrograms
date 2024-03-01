package ru.korobeynikov.daggermultiplemodule

import android.content.Context
import ru.korobeynikov.data.DataModule

class MyAppComponent(private val context: Context) : AppComponent {

    private val dataModule = DataModule()

    override fun injectTasksActivity(tasksActivity: TasksActivity) {
        tasksActivity.database = dataModule.provideDatabase(context)
    }
}