package ru.korobeynikov.p04mockobjects.other

import android.content.Context
import ru.korobeynikov.p04mockobjects.R
import java.net.URI

class UriValidator(private val context: Context) {

    fun validate(uri: String): String {
        val resId = if (isUrl(uri))
            R.string.url
        else if (isFile(uri))
            R.string.file
        else
            R.string.nothing
        return context.getString(resId)
    }

    private fun isUrl(uri: String) = "http" == URI.create(uri).scheme

    private fun isFile(uri: String) = "file" == URI.create(uri).scheme
}