package ru.korobeynikov.p03junit4assertmethods

import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ShortStringMatcher(private val length: Int) : TypeSafeMatcher<String>() {

    companion object {

        private const val SHORT_LENGTH_LIMIT = 5

        fun isShortString() = ShortStringMatcher(SHORT_LENGTH_LIMIT)

        fun isShortString(limit: Int) = ShortStringMatcher(limit)
    }

    override fun describeTo(description: Description?) {
        description?.appendText("Length of string must be shorter than $length")
    }

    override fun matchesSafely(item: String?) = item != null && item.length < length
}