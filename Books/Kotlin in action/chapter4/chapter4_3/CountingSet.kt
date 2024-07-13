package ru.korobeynikov.chapter4.chapter4_3

class CountingSet<T>(private val innerSet: MutableCollection<T> = HashSet()) :
    MutableCollection<T> by innerSet {

    var objectsAdded = 0

    override fun add(element: T): Boolean {
        objectsAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectsAdded += elements.size
        return innerSet.addAll(elements)
    }
}