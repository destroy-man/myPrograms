package com.korobeynikov.p0681parcel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.util.Log

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    lateinit var p:Parcel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        writeParcel()
        readParcel()
    }

    fun writeParcel(){
        p=Parcel.obtain()
        val b:Byte=1
        val i:Int=2
        val l:Long=3
        val f:Float=4f
        val d:Double=5.0
        val s="abcdefgh"
        logWriteInfo("Before writing")
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

    fun logWriteInfo(txt:String){
        Log.d(LOG_TAG,txt+": dataSize = "+p.dataSize())
    }

    fun readParcel(){
        logReadInfo("before reading")
        p.setDataPosition(0)
        logReadInfo("byte = "+p.readByte())
        logReadInfo("int = "+p.readInt())
        logReadInfo("long = "+p.readLong())
        logReadInfo("float = "+p.readFloat())
        logReadInfo("double = "+p.readDouble())
        logReadInfo("String = "+p.readString())
    }

    fun logReadInfo(txt:String){
        Log.d(LOG_TAG,txt+": dataPosition = "+p.dataPosition())
    }
}