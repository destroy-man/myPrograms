package ru.korobeynikov.p42stabilityintroductiondataclasses

import androidx.compose.runtime.Immutable

@Immutable
data class MyClass(
    var i: Int = 0,
)

data class MyClassVal(
    val i: Int = 0,
)
