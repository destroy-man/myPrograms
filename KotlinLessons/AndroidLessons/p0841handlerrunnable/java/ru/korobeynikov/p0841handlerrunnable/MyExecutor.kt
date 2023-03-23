package ru.korobeynikov.p0841handlerrunnable

import java.util.concurrent.Executor

class MyExecutor : Executor {
    override fun execute(r: Runnable?) {
        Thread(r).start()
    }
}