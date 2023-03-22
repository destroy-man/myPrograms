package ru.korobeynikov.p0821handleradvmessage

import java.util.concurrent.Executor

class MyExecutor : Executor {

    var status = 0
    var countFiles = 0
    var countFilesLeft = 0
    var numberFile = 0
    lateinit var contentFile: ByteArray

    override fun execute(r: Runnable?) {
        Thread(r).start()
    }

    fun setData(status: Int, countFiles: Int) {
        this.status = status
        this.countFiles = countFiles
    }

    fun setData(status: Int, numberFile: Int, countFilesLeft: Int, contentFile: ByteArray) {
        this.status = status
        this.numberFile = numberFile
        this.countFilesLeft = countFilesLeft
        this.contentFile = contentFile
    }
}