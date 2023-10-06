package ru.korobeynikov.p10cpuprofiler

import java.util.*
import java.util.concurrent.TimeUnit

class MyRunnable : Runnable {

    private val rnd = Random(System.currentTimeMillis())

    override fun run() {
        for (i in 0 until getRandom() + 10) {
            doPause()
            doWork()
        }
    }

    private fun doPause() {
        try {
            TimeUnit.MILLISECONDS.sleep(50L * getRandom())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun doWork() {
        var d = 0.0
        var time = Int.MAX_VALUE
        for (i in 0 until 1000000 * getRandom()) {
            d += time / (time - 1)
            time--
        }
    }

    private fun getRandom() = rnd.nextInt(10) + 1
}