package ru.korobeynikov.p0681parcel

import android.support.v7.app.AppCompatActivity
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
        val i=2
        val l:Long=3
        val f=4f
        val d=5.0
        val s="abcdefgh"
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

    fun logWriteInfo(txt:String){
        Log.d(LOG_TAG,"$txt: datasize = ${p.dataSize()}")
    }

    fun readParcel(){
        logReadInfo("before reading")
        p.setDataPosition(0)
        logReadInfo("byte = ${p.readByte()}")
        logReadInfo("int = ${p.readInt()}")
        logReadInfo("long = ${p.readLong()}")
        logReadInfo("float = ${p.readFloat()}")
        logReadInfo("double = ${p.readDouble()}")
        logReadInfo("string = ${p.readString()}")
    }

    fun logReadInfo(txt:String){
        Log.d(LOG_TAG,"$txt: dataPosition = ${p.dataPosition()}")
    }
}