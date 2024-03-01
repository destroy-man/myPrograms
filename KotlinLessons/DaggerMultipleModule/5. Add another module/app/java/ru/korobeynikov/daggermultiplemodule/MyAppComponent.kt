package ru.korobeynikov.daggermultiplemodule

import android.content.Context
import ru.korobeynikov.core.CoreModule
import ru.korobeynikov.data.DataModule

class MyAppComponent(private val context: Context) : AppComponent {

    private val dataModule = DataModule()
    private val coreModule = CoreModule()

    override fun injectTasksActivity(tasksActivity: TasksActivity) {
        tasksActivity.database =
            dataModule.provideDatabase(context, coreModule.provideFileManager())
    }
}