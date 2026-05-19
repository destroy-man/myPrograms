package ru.korobeynikov.p0681parcel

import android.os.Bundle
import android.os.Parcel
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {

    val logTag = "myLogs"

    lateinit var p: Parcel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("Text")
            }
        }
        writeParcel()
        readParcel()
    }

    fun writeParcel() {
        p = Parcel.obtain()

        val b: Byte = 1
        val i = 2
        val l = 3L
        val f = 4f
        val d = 5.0
        val s = "abcdefgh"

        logWriteInfo("before writing")
        p.writeByte(b)
        logWriteInfo("byte")
        p.writeInt(i)
        logWriteInfo("int")
        p.writeLong(l)
        logWriteInfo("long")
        p.writeFloat(f)
        logWriteInfo("float")
        p.writeDouble(d)
        logWriteInfo("double")
        p.writeString(s)
        logWriteInfo("String")
    }

    fun logWriteInfo(txt: String) {
        Log.d(logTag, "$txt: dataSize = ${p.dataSize()}")
    }

    fun readParcel() {
        logReadInfo("before reading")
        p.setDataPosition(0)
        logReadInfo("byte = ${p.readByte()}")
        logReadInfo("int = ${p.readInt()}")
        logReadInfo("long = ${p.readLong()}")
        logReadInfo("float = ${p.readFloat()}")
        logReadInfo("double = ${p.readDouble()}")
        logReadInfo("string = ${p.readString()}")
    }

    fun logReadInfo(txt: String) {
        Log.d(logTag, "$txt: dataPosition = ${p.dataPosition()}")
    }
}