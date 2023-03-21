package ru.korobeynikov.p0811handlersimplemessage

import java.util.concurrent.Executor

class MyExecutor : Executor {

    var status = 0

    override fun execute(r: Runnable?) {
        Thread(r).start()
    }
}