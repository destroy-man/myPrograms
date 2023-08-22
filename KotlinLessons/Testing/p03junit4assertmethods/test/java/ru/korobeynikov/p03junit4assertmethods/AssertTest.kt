package ru.korobeynikov.p03junit4assertmethods

import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import ru.korobeynikov.p03junit4assertmethods.ShortStringMatcher.Companion.isShortString

class AssertTest {
    @Test
    fun test() = assertThat("12345", isShortString())
}