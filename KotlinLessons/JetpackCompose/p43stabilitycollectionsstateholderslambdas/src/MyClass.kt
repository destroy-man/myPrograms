package ru.korobeynikov.p43stabilitycollectionsstateholderslambdas

import androidx.compose.runtime.Immutable

@Immutable
data class MyClass(
    val list: List<String> = listOf("0"),
)
