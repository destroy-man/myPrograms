package ru.korobeynikov.p06memoryprofilerdump.other

import java.util.*

open class BytesArray(count: Int) {

    private var bytes = ByteArray(1000 * 1000 * count)

    init {
        Arrays.fill(bytes, 1)
    }
}