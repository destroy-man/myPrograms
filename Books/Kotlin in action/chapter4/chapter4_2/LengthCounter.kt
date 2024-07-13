package ru.korobeynikov.chapter4.chapter4_2

class LengthCounter {

    var counter: Int = 0
        private set

    fun addWord(word: String) {
        counter += word.length
    }
}