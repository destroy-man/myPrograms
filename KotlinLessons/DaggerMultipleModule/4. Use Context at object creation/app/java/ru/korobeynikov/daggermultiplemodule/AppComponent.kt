package ru.korobeynikov.daggermultiplemodule

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.korobeynikov.data.DataModule

@Component(modules = [DataModule::class])
interface AppComponent {

    fun injectTasksActivity(tasksActivity: TasksActivity)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}