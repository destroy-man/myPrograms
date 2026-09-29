package ru.korobeynikov.p03assertmethods

import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.CoreMatchers.both
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.either
import org.hamcrest.CoreMatchers.endsWith
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.everyItem
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.CoreMatchers.sameInstance
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import ru.korobeynikov.p03assertmethods.ShortStringMatcher.Companion.isShortString

class AssertThatTest {

    @Test
    fun equalToMethod() {
        val s1 = "123"
        val s2 = "123"
        assertThat(s1, equalTo(s2))
    }

    @Test
    fun instanceOfMethod() {
        val s1 = "123"
        assertThat(s1, instanceOf(String::class.java))
    }

    @Test
    fun nullValueMethod() {
        val s1 = "123"
        assertThat(s1, `is`(nullValue()))
    }

    @Test
    fun isMethod() {
        val a = 1
        val b = 2
        assertThat(a, equalTo(b))
        assertThat(a, `is`(equalTo(b)))
        assertThat(a, `is`(b))
    }

    @Test
    fun notNullValueMethod() {
        val s1 = "123"
        assertThat(s1, notNullValue())
    }

    @Test
    fun sameInstanceMethod() {
        val s1 = "123"
        val s2 = "123"
        assertThat(s1, sameInstance(s2))
    }

    @Test
    fun startsWithMethod() {
        val s = "abcdefg"
        assertThat(s, startsWith("abc"))
    }

    @Test
    fun endsWithMethod() {
        val s = "abcdefg"
        assertThat(s, endsWith("def"))
    }

    @Test
    fun containsStringMethod() {
        val s = "abcdefg"
        assertThat(s, containsString("def"))
    }

    @Test
    fun hasItemMethod() {
        val list = listOf("one", "two", "three", "four", "five")
        assertThat(list, hasItem("two"))
    }

    @Test
    fun hasItemAndStartsWith() {
        val list = listOf("one", "two", "three", "four", "five")
        assertThat(list, hasItem(startsWith("t")))
    }

    @Test
    fun hasItemsMethod() {
        val list = listOf("one", "two", "three", "four", "five")
        assertThat(list, hasItems("five", "six"))
    }

    @Test
    fun hasItemsStartsEndsWith() {
        val list = listOf("one", "two", "three", "four", "five")
        assertThat(list, hasItems(startsWith("f"), endsWith("r")))
    }

    @Test
    fun everyItemMethod() {
        val list = listOf("one", "two", "three", "four", "five")
        assertThat(list, everyItem(startsWith("t")))
    }

    @Test
    fun allOfMethod() {
        val s = "abcdefg"
        assertThat(
            s,
            allOf(
                startsWith("a"), endsWith("g"), containsString("cde")
            )
        )
    }

    @Test
    fun bothMethod() {
        val s = "1234"
        assertThat(s, both(startsWith("1")).and(endsWith("4")))
    }

    @Test
    fun anyOfMethod() {
        val s = "abcdefg"
        assertThat(
            s,
            anyOf(
                startsWith("b"), endsWith("g"), containsString("rst")
            )
        )
    }

    @Test
    fun eitherMethod() {
        val s = "1234"
        assertThat(s, either(startsWith("1")).or(endsWith("7")))
    }

    @Test
    fun anyOfAndAllOf() {
        val s = "abcdefg"
        assertThat(
            s,
            anyOf(
                allOf(startsWith("a"), endsWith("g")),
                allOf(startsWith("A"), endsWith("G"))
            )
        )
    }

    @Test
    fun notMethod() {
        val s = "1234"
        assertThat(s, not(startsWith("_")))
    }

    @Test
    fun shortStringMatcherMethod() {
        val s = "12345"
        assertThat(s, isShortString())
    }
}