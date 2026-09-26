package ru.korobeynikov.p02creatingandlaunchlocaltest.utils

class ConvertUtils {
    companion object {
        fun stringToInt(s: String): Int {
            return try {
                s.toInt()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                0
            }
        }
    }
}