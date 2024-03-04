package ru.korobeynikov.task.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.data.Database
import ru.korobeynikov.network.TaskApi
import ru.korobeynikov.task.TaskRepository

@Module
class TaskModule {
    @Provides
    fun provideTaskRepository(database: Database, taskApi: TaskApi) =
        TaskRepository(database, taskApi)
}