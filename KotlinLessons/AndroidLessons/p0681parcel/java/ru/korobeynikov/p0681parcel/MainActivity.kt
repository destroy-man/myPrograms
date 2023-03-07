package ru.korobeynikov.p0681parcel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0681parcel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"
    private lateinit var p: Parcel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        writeParcel()
        readParcel()
    }

    private fun writeParcel() {
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
        logWriteInfo("string")
    }

    private fun logWriteInfo(txt: String) {
        Log.d(logTag, "$txt: dataSize = ${p.dataSize()}")
    }

    private fun readParcel() {
        logReadInfo("before reading")
        p.setDataPosition(0)
        logReadInfo("byte = ${p.readByte()}")
        logReadInfo("int = ${p.readInt()}")
        logReadInfo("long = ${p.readLong()}")
        logReadInfo("float = ${p.readFloat()}")
        logReadInfo("double = ${p.readDouble()}")
        logReadInfo("string = ${p.readString()}")
    }

    private fun logReadInfo(txt: String) {
        Log.d(logTag, "$txt: dataPosition = ${p.dataPosition()}")
    }
}