package ru.korobeynikov.p0661alertdialogoperations

import java.util.concurrent.Executor

class MyExecutor : Executor {
    override fun execute(r: Runnable?) {
        Thread(r).start()
    }
}