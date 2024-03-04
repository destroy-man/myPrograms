package ru.korobeynikov.task

import dagger.Subcomponent
import ru.korobeynikov.network.NetworkModule

@Subcomponent(modules = [NetworkModule::class, TaskModule::class])
interface TaskComponent {
    fun injectTasksFragment(tasksFragment: TasksFragment)
}