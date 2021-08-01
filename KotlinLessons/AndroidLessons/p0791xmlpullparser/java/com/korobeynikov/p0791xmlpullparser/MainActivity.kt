package com.korobeynikov.p0791xmlpullparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tmp=""
        try{
            val xpp=prepareXpp()
            while(xpp.eventType!=XmlPullParser.END_DOCUMENT){
                when(xpp.eventType){
                    XmlPullParser.START_DOCUMENT->Log.d(LOG_TAG,"START_DOCUMENT")
                    XmlPullParser.START_TAG->{
                        Log.d(LOG_TAG,"START_TAG: name = "+xpp.name+", depth = "+xpp.depth+", attrCount = "+xpp.attributeCount)
                        tmp=""
                        for(i in 0..xpp.attributeCount-1)
                            tmp+=xpp.getAttributeName(i)+" = "+xpp.getAttributeValue(i)+", "
                        if(!TextUtils.isEmpty(tmp))
                            Log.d(LOG_TAG,"Attributes: "+tmp)
                    }
                    XmlPullParser.END_TAG->Log.d(LOG_TAG,"END_TAG: name = "+xpp.name)
                    XmlPullParser.TEXT->Log.d(LOG_TAG,"text = "+xpp.text)
                }
                xpp.next()
            }
            Log.d(LOG_TAG,"END_DOCUMENT")
        }
        catch(e:XmlPullParserException){
            e.printStackTrace()
        }
        catch(e:IOException){
            e.printStackTrace()
        }
    }

    fun prepareXpp():XmlPullParser{
        val factory=XmlPullParserFactory.newInstance()
        factory.isNamespaceAware=true
        val xpp=factory.newPullParser()
        xpp.setInput(StringReader("<data><phone><company>Samsung</company></phone></data>"))
        return xpp
    }
}