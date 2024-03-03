package ru.korobeynikov.daggermultiplemodule

import android.content.Context
import ru.korobeynikov.core.CoreModule
import ru.korobeynikov.data.DataModule
import ru.korobeynikov.network.NetworkModule
import ru.korobeynikov.task.TaskModule
import ru.korobeynikov.task.TasksFragment

class MyAppComponent(private val context: Context) : AppComponent {

    private val dataModule = DataModule()
    private val coreModule = CoreModule()
    private val networkModule = NetworkModule()
    private val taskModule = TaskModule()

    override fun injectTasksFragment(tasksFragment: TasksFragment) {
        tasksFragment.taskRepository = taskModule.provideTaskRepository(
            dataModule.provideDatabase(
                context,
                coreModule.provideFileManager()
            ), networkModule.provideTasksApi()
        )
    }
}