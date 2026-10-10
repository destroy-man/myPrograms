package ru.korobeynikov.p12junitexceptionsparametrizedtimeoutdisabled

class Converter {
    fun stringToInteger(s: String): Int {
        return try {
            s.toInt()
        } catch (e: NumberFormatException) {
            throw NumberFormatException()
        }
    }
}