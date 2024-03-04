package ru.korobeynikov.daggermultiplemodule

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.korobeynikov.core.CoreModule
import ru.korobeynikov.data.DataModule
import ru.korobeynikov.data.Database
import ru.korobeynikov.task.di.TaskComponentDependencies

@Component(modules = [DataModule::class, CoreModule::class])
interface AppComponent : TaskComponentDependencies {

    override fun getDatabase(): Database

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}