package ru.korobeynikov.chapter8

class ContactListFilters {

    var prefix: String = ""
    var onlyWithPhoneNumber: Boolean = false

    fun getPredicate(): (Person8_1_5) -> Boolean {
        val startsWithPrefix = { p: Person8_1_5 ->
            p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix)
        }
        if (!onlyWithPhoneNumber)
            return startsWithPrefix
        return { startsWithPrefix(it) && it.phoneNumber != null }
    }
}