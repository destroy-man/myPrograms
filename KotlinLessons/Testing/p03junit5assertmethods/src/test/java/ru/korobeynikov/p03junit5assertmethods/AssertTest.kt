package ru.korobeynikov.p03junit5assertmethods

import org.junit.jupiter.api.Test
import org.hamcrest.MatcherAssert.assertThat
import ru.korobeynikov.p03junit5assertmethods.ShortStringMatcher.Companion.isShortString

class AssertTest {
    @Test
    fun assert() {
        val s = "12345"
        assertThat(s, isShortString())
    }
}