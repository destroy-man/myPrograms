package ru.korobeynikov.p17pagingsource

class EmployeeStorage {

    val storage = ArrayList<Employee>()

    init {
        for (i in 1..100)
            storage.add(Employee(i.toLong(), "name $i", i * 1000))
    }
}