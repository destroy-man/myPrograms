package ru.korobeynikov.task.di

import ru.korobeynikov.data.Database

interface TaskComponentDependencies {
    fun getDatabase(): Database
}