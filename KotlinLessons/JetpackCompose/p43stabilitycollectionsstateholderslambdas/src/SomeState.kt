package ru.korobeynikov.p43stabilitycollectionsstateholderslambdas

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SomeState(
    val clicksCount: Int = 0,
    val list: ImmutableList<String> = persistentListOf("0"),
)

data class SomeStateClassWithList(
    val clicksCount: Int = 0,
    val myClass: MyClass = MyClass(),
)

data class SomeStateList(
    val clicksCount: Int = 0,
    val list: List<String> = listOf("0"),
)