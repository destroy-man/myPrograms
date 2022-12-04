package ru.korobeynikov.p03junit5assertmethods

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class ShortStringMatcher(private val length: Int) : TypeSafeMatcher<String>() {

    companion object {

        private const val SHORT_LENGTH_LIMIT = 5

        fun isShortString(): Matcher<String> {
            return isShortString(SHORT_LENGTH_LIMIT)
        }

        fun isShortString(limit: Int): Matcher<String> {
            return ShortStringMatcher(limit)
        }
    }

    override fun matchesSafely(item: String?): Boolean {
        if (item != null)
            return item.length < length
        return false
    }

    override fun describeTo(description: Description?) {
        description?.appendText("Length of string  must be shorter than $length")
    }
}