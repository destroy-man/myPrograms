package ru.korobeynikov.p04mockobjects

import android.content.Context
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.hamcrest.CoreMatchers.`is`

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
        assertThat(uriValidator.validate("http://google.com"), `is`(URL))
        assertThat(uriValidator.validate("file://sdcard/DCIM/img001.jpg"), `is`(FILE))
        assertThat(uriValidator.validate("bla-bla-bla"), `is`(NOTHING))
    }
}