package ru.korobeynikov.p0451expandablelist

data class GroupPhones(
    var name: String = "",
    var type: Int = Types.PARENT,
    val phones: MutableList<Phone> = ArrayList(),
    var isExpanded: Boolean = false,
)
