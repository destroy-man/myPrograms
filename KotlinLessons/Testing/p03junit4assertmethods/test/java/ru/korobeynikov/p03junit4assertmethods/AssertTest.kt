package ru.korobeynikov.p03junit4assertmethods

import org.junit.Test
import org.junit.Assert.*
import ru.korobeynikov.p03junit4assertmethods.ShortStringMatcher.Companion.isShortString

class AssertTest {
    @Test
    fun assert() {
        val s = "12345"
        assertThat(s, isShortString())
    }
}