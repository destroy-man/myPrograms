package ru.korobeynikov.task.di

import ru.korobeynikov.network.NetworkModule
import ru.korobeynikov.task.TasksFragment

class MyTaskComponent(private val taskComponentDependencies: TaskComponentDependencies) :
    TaskComponent {

    private val networkModule: NetworkModule = NetworkModule()
    private val taskModule: TaskModule = TaskModule()

    override fun injectTasksFragment(tasksFragment: TasksFragment) {
        tasksFragment.taskRepository = taskModule.provideTaskRepository(
            taskComponentDependencies.getDatabase(),
            networkModule.provideTasksApi()
        )
    }
}