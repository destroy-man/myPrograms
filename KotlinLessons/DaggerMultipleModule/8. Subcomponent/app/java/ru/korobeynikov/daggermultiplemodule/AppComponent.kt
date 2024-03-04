package ru.korobeynikov.daggermultiplemodule

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.korobeynikov.core.CoreModule
import ru.korobeynikov.data.DataModule
import ru.korobeynikov.task.TaskComponent

@Component(modules = [DataModule::class, CoreModule::class])
interface AppComponent {

    fun createTaskComponent(): TaskComponent

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}