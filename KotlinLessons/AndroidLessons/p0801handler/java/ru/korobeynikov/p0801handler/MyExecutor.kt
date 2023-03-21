package ru.korobeynikov.p0801handler

import java.util.concurrent.Executor

class MyExecutor : Executor {

    var countFiles = 0

    override fun execute(r: Runnable?) {
        Thread(r).start()
    }
}