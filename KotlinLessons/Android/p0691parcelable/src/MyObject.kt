package ru.korobeynikov.p0691parcelable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyObject(
    val s: String,
    val i: Int,
) : Parcelable
