package ru.korobeynikov.daggermultiplemodule

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.korobeynikov.core.CoreModule
import ru.korobeynikov.data.DataModule
import ru.korobeynikov.network.NetworkModule
import ru.korobeynikov.task.TaskComponent
import ru.korobeynikov.task.TaskModule
import ru.korobeynikov.task.TasksFragment

@Component(modules = [DataModule::class, CoreModule::class, NetworkModule::class, TaskModule::class])
interface AppComponent : TaskComponent {

    override fun injectTasksFragment(tasksFragment: TasksFragment)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}