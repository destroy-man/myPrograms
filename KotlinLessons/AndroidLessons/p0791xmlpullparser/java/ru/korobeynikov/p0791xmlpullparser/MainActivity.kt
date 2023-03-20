package ru.korobeynikov.p0791xmlpullparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import ru.korobeynikov.p0791xmlpullparser.databinding.ActivityMainBinding
import java.io.IOException
import java.io.StringReader

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val tmp = StringBuilder()
        try {
            val xpp = prepareXpp()
            while (xpp.eventType != XmlPullParser.END_DOCUMENT) {
                when (xpp.eventType) {
                    XmlPullParser.START_DOCUMENT -> Log.d(logTag, "START_DOCUMENT")
                    XmlPullParser.START_TAG -> {
                        Log.d(logTag, "START_TAG: name = ${xpp.name}, depth = ${xpp.depth}, " +
                                "attrCount = ${xpp.attributeCount}")
                        tmp.clear()
                        for (i in 0 until xpp.attributeCount)
                            tmp.append("${xpp.getAttributeName(i)} = ${xpp.getAttributeValue(i)}, ")
                        if (tmp.isNotEmpty())
                            Log.d(logTag, "Attributes: $tmp")
                    }
                    XmlPullParser.END_TAG -> Log.d(logTag, "END_TAG: name = ${xpp.name}")
                    XmlPullParser.TEXT -> Log.d(logTag, "text = ${xpp.text}")
                }
                xpp.next()
            }
            Log.d(logTag, "END_DOCUMENT")
        } catch (e: Exception) {
            when (e) {
                is XmlPullParserException, is IOException -> e.printStackTrace()
            }
        }
    }

    private fun prepareXpp(): XmlPullParser {
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        val xpp = factory.newPullParser()
        xpp.setInput(StringReader("<data><phone><company>Samsung</company></phone></data>"))
        return xpp
    }
}