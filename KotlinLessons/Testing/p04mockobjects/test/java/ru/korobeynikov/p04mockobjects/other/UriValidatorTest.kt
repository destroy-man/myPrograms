package ru.korobeynikov.p04mockobjects.other

import android.content.Context
import org.hamcrest.MatcherAssert

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.hamcrest.CoreMatchers.`is`
import ru.korobeynikov.p04mockobjects.R

@RunWith(MockitoJUnitRunner::class)
class UriValidatorTest {

    companion object {
        const val NOTHING = "Nothing"
        const val URL = "URL"
        const val FILE = "File"
    }

    private lateinit var uriValidator: UriValidator
    private lateinit var mockContext: Context

    @Before
    fun setUp() {
        mockContext = Mockito.mock(Context::class.java)
        Mockito.`when`(mockContext.getString(R.string.nothing)).thenReturn(NOTHING)
        Mockito.`when`(mockContext.getString(R.string.url)).thenReturn(URL)
        Mockito.`when`(mockContext.getString(R.string.file)).thenReturn(FILE)
        uriValidator = UriValidator(mockContext)
    }

    @Test
    fun validate() {
        MatcherAssert.assertThat(uriValidator.validate("http://google.com"), `is`(URL))
        MatcherAssert.assertThat(uriValidator.validate("file://sdcard/DCIM/img001.jpg"), `is`(FILE))
        MatcherAssert.assertThat(uriValidator.validate("bla-bla-bla"), `is`(NOTHING))
    }
}