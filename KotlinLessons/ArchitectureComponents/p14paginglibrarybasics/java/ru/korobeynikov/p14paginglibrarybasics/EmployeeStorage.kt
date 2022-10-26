package ru.korobeynikov.p14paginglibrarybasics

class EmployeeStorage {

    private val storage = ArrayList<Employee>()

    init {
        for (i in 1..100)
            storage.add(Employee(i.toLong(), "name $i", i * 1000))
    }

    fun getData(startPosition: Int, loadSize: Int): List<Employee> {
        return if (startPosition + loadSize > storage.size)
            emptyList()
        else
            storage.subList(startPosition, startPosition + loadSize)
    }
}