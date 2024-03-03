package ru.korobeynikov.task

import ru.korobeynikov.data.Database
import ru.korobeynikov.network.TaskApi

class TaskRepository(private val database: Database, private val taskApi: TaskApi)