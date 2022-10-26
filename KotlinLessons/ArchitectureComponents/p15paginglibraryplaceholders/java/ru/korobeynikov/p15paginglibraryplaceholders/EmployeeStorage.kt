package ru.korobeynikov.p15paginglibraryplaceholders

class EmployeeStorage {

    private val storage = ArrayList<Employee>()

    init {
        for (i in 1..100)
            storage.add(Employee(i.toLong(), "name $i", i * 1000))
    }

    fun getData(startPosition: Int, loadSize: Int): List<Employee> {
        return if (startPosition + loadSize > storage.size)
            storage.subList(startPosition, storage.size)
        else
            storage.subList(startPosition, startPosition + loadSize)
    }

    fun getInitialData(startPosition: Int, loadSize: Int): EmployeeData {
        return if (storage.size == 0)
            EmployeeData(emptyList(), 0)
        else if (startPosition > storage.size)
            EmployeeData(getData(storage.size - loadSize, loadSize),
                storage.size - loadSize, storage.size)
        else
            EmployeeData(getData(startPosition, loadSize), startPosition, storage.size)
    }
}