package ru.korobeynikov.p0671progressdialog

import java.util.concurrent.Executor

class MyExecutor : Executor {
    override fun execute(r: Runnable?) {
        Thread(r).start()
    }
}