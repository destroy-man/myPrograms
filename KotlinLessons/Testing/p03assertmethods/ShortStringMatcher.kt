package ru.korobeynikov.p03assertmethods

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class ShortStringMatcher(private val length: Int) : TypeSafeMatcher<String>() {

    override fun matchesSafely(item: String?): Boolean {
        return if (item != null) item.length < length else false
    }

    override fun describeTo(description: Description?) {
        description?.appendText("Length of string must be shorter than $length")
    }

    companion object {

        const val SHORT_LENGTH_LIMIT = 5

        fun isShortString(limit: Int): Matcher<String> {
            return ShortStringMatcher(limit)
        }

        fun isShortString(): Matcher<String> {
            return ShortStringMatcher(SHORT_LENGTH_LIMIT)
        }
    }
}