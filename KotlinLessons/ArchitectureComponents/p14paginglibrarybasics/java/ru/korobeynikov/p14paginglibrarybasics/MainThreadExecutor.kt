package ru.korobeynikov.p14paginglibrarybasics

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

class MainThreadExecutor : Executor {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable?) {
        if (command != null)
            mHandler.post(command)
    }
}