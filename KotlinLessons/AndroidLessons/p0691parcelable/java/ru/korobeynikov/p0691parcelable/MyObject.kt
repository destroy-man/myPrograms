package ru.korobeynikov.p0691parcelable

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.util.Log

class MyObject() :Parcelable {

    companion object CREATOR : Parcelable.Creator<MyObject> {

        val LOG_TAG="myLogs"

        override fun createFromParcel(parcel: Parcel): MyObject {
            Log.d(LOG_TAG,"createFromParcel")
            return MyObject(parcel)
        }

        override fun newArray(size: Int): Array<MyObject?> {
            return Array(size){null}
        }
    }

    lateinit var s:String
    var i=0

    constructor(_s:String,_i:Int):this(){
        Log.d(LOG_TAG,"MyObject(String _s, int _i)")
        s=_s
        i=_i
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        Log.d(LOG_TAG,"writeToParcel")
        parcel.writeString(s)
        parcel.writeInt(i)
    }

    override fun describeContents(): Int {
        return 0
    }

    private constructor(parcel: Parcel):this(){
        Log.d(LOG_TAG,"MyObject(Parcel parcel)")
        s=parcel.readString().toString()
        i=parcel.readInt()
    }
}