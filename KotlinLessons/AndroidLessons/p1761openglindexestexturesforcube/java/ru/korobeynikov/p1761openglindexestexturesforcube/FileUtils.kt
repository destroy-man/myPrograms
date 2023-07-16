package ru.korobeynikov.p1761openglindexestexturesforcube

import android.content.Context
import android.content.res.Resources
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FileUtils {
    companion object {
        fun readTextFromRaw(context: Context, resourceId: Int): String {
            val stringBuilder = StringBuilder()
            try {
                var bufferedReader: BufferedReader? = null
                try {
                    val inputStream = context.resources.openRawResource(resourceId)
                    bufferedReader = BufferedReader(InputStreamReader(inputStream))
                    var line = bufferedReader.readLine()
                    while (line != null) {
                        stringBuilder.append("$line\r\n")
                        line = bufferedReader.readLine()
                    }
                } finally {
                    bufferedReader?.close()
                }
            } catch (ex: Exception) {
                when (ex) {
                    is IOException, is Resources.NotFoundException -> ex.printStackTrace()
                }
            }
            return stringBuilder.toString()
        }
    }
}