package ru.korobeynikov.p02creatingandlaunchlocaltest.utils

class ConvertUtils {
    companion object {
        fun stringToInt(s: String): Int {
            var result = 0
            try {
                result = s.toInt()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            return result
        }
    }
}