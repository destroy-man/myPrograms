package ru.korobeynikov.p0691parcelable

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

class MyObject() : Parcelable {

    var s: String? = null
    var i = 0

    constructor(_s: String, _i: Int) : this() {
        Log.d(MainActivity.LOG_TAG, "MyObject(_s:String, _i:Int)")
        s = _s
        i = _i
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        Log.d(MainActivity.LOG_TAG, "writeToParcel")
        parcel.writeString(s)
        parcel.writeInt(i)
    }

    companion object CREATOR : Parcelable.Creator<MyObject> {

        override fun createFromParcel(parcel: Parcel): MyObject {
            Log.d(MainActivity.LOG_TAG, "createFromParcel")
            return MyObject(parcel)
        }

        override fun newArray(size: Int): Array<MyObject?> {
            return arrayOfNulls(size)
        }
    }

    private constructor(parcel: Parcel) : this() {
        Log.d(MainActivity.LOG_TAG, "MyObject(parcel:Parcel)")
        s = parcel.readString()
        i = parcel.readInt()
    }
}