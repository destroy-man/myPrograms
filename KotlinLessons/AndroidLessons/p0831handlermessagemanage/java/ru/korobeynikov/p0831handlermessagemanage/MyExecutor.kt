package ru.korobeynikov.p0831handlermessagemanage

import java.util.concurrent.Executor

class MyExecutor : Executor {

    var status = 0

    override fun execute(r: Runnable?) {
        Thread(r).start()
    }
}