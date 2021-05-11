package com.korobeynikov.p0041producers

import dagger.Module
import dagger.Provides
import dagger.producers.Production
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
class ExecutorModule {
    @Provides
    @Production
    fun executor():Executor{
        return Executors.newSingleThreadExecutor()
    }
}