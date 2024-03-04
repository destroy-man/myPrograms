package ru.korobeynikov.daggermultiplemodule

import android.content.Context
import ru.korobeynikov.core.CoreModule
import ru.korobeynikov.data.DataModule
import ru.korobeynikov.network.NetworkModule
import ru.korobeynikov.task.TaskComponent
import ru.korobeynikov.task.TaskModule
import ru.korobeynikov.task.TasksFragment

class MyAppComponent(private val context: Context) : AppComponent {

    private val dataModule = DataModule()
    private val coreModule = CoreModule()

    fun getDatabase() = dataModule.provideDatabase(context, coreModule.provideFileManager())

    override fun createTaskComponent(): TaskComponent = MyTaskComponent(this)

    private class MyTaskComponent(
        private val myAppComponent: MyAppComponent,
        private val networkModule: NetworkModule = NetworkModule(),
        private val taskModule: TaskModule = TaskModule(),
    ) : TaskComponent {
        override fun injectTasksFragment(tasksFragment: TasksFragment) {
            tasksFragment.taskRepository = taskModule.provideTaskRepository(
                myAppComponent.getDatabase(),
                networkModule.provideTasksApi()
            )
        }
    }
}