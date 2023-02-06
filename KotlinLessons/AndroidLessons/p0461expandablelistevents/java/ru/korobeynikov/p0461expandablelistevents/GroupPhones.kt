package ru.korobeynikov.p0461expandablelistevents

data class GroupPhones(
    var name: String = "",
    var type: Int = Types.PARENT,
    val phones: MutableList<Phone> = ArrayList(),
    var isExpanded: Boolean = false,
    val parentPosition: Int = -1,
    val parentName: String = ""
)
