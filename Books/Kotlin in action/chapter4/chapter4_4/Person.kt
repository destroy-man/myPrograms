package ru.korobeynikov.chapter4.chapter4_4

data class Person(val name: String) {

    companion object Loader

    object NameComparator : Comparator<Person> {
        override fun compare(p0: Person?, p1: Person?): Int =
            if (p0 != null && p1 != null)
                p0.name.compareTo(p1.name)
            else if (p0 != null) -1
            else 1
    }
}

fun Person.Loader.fromJson(jsonText: String): Person {
    val beginIndex = jsonText.indexOf("name: '")
    val name = jsonText.substring(beginIndex + 7).substringBefore("'")
    return Person(name)
}
