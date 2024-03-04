package ru.korobeynikov.task.di

import dagger.Component
import ru.korobeynikov.network.NetworkModule
import ru.korobeynikov.task.TasksFragment

@Component(
    modules = [NetworkModule::class, TaskModule::class],
    dependencies = [TaskComponentDependencies::class]
)
interface TaskComponent {
    fun injectTasksFragment(tasksFragment: TasksFragment)
}