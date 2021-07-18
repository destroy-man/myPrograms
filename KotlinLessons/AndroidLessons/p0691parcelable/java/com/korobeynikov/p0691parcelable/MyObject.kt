package com.korobeynikov.p0691parcelable

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import kotlin.properties.Delegates

class MyObject():Parcelable{

    val LOG_TAG="myLogs"
    lateinit var s:String
    var i by Delegates.notNull<Int>()

    constructor(_s:String,_i:Int):this(){
        Log.d(LOG_TAG,"MyObject(String _s, int _i)")
        s=_s
        i=_i
    }

    override fun describeContents():Int{
        return 0
    }

    override fun writeToParcel(parcel:Parcel,flags:Int){
        Log.d(LOG_TAG,"writeToParcel")
        parcel.writeString(s)
        parcel.writeInt(i)
    }

    companion object CREATOR : Parcelable.Creator<MyObject> {
        override fun createFromParcel(parcel: Parcel): MyObject {
            Log.d(MyObject().LOG_TAG,"createFromParcel")
            return MyObject(parcel)
        }

        override fun newArray(size: Int): Array<MyObject?> {
            return arrayOfNulls(size)
        }
    }

    private constructor(parcel:Parcel):this(){
        Log.d(LOG_TAG,"MyObject(Parcel parcel)")
        s=""+parcel.readString()
        i=parcel.readInt()
    }
}