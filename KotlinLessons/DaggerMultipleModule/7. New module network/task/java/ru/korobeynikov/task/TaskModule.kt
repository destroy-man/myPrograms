package ru.korobeynikov.task

import dagger.Module
import dagger.Provides
import ru.korobeynikov.data.Database
import ru.korobeynikov.network.TaskApi

@Module
class TaskModule {
    @Provides
    fun provideTaskRepository(database: Database, taskApi: TaskApi) =
        TaskRepository(database, taskApi)
}