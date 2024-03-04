package ru.korobeynikov.task.di

interface TaskComponentDependenciesProvider {
    fun getTaskComponentDependencies(): TaskComponentDependencies
}