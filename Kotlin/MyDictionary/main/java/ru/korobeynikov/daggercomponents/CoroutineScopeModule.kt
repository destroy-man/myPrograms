package ru.korobeynikov.daggercomponents

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

@Module
class CoroutineScopeModule {
    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(Job())
    }
}