package ru.korobeynikov.daggermultiplemodule

import ru.korobeynikov.data.DataModule

class MyAppComponent : AppComponent {

    private val dataModule = DataModule()

    override fun injectTasksActivity(tasksActivity: TasksActivity) {
        tasksActivity.database = dataModule.provideDatabase()
    }
}