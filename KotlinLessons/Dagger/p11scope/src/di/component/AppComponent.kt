package ru.korobeynikov.p11scope.di.component

import dagger.Component
import ru.korobeynikov.p11scope.di.module.NetworkModule
import ru.korobeynikov.p11scope.di.module.StorageModule
import ru.korobeynikov.p11scope.di.module.UtilsModule
import ru.korobeynikov.p11scope.di.scope.AppScope

@AppScope
@Component(modules = [StorageModule::class, NetworkModule::class, UtilsModule::class])
interface AppComponent {
    fun getOrderComponent(): OrderComponent
}