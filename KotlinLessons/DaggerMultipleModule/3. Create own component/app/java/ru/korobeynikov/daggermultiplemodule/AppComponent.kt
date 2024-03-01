package ru.korobeynikov.daggermultiplemodule

import dagger.Component
import ru.korobeynikov.data.DataModule

@Component(modules = [DataModule::class])
interface AppComponent {
    fun injectTasksActivity(tasksActivity: TasksActivity)
}