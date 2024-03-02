package ru.korobeynikov.daggermultiplemodule

import android.content.Context
import ru.korobeynikov.core.CoreModule
import ru.korobeynikov.data.DataModule
import ru.korobeynikov.task.TasksFragment

class MyAppComponent(private val context: Context) : AppComponent {

    private val dataModule = DataModule()
    private val coreModule = CoreModule()

    override fun injectTasksFragment(tasksFragment: TasksFragment) {
        tasksFragment.database =
            dataModule.provideDatabase(context, coreModule.provideFileManager())
    }
}