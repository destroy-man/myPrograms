package ru.korobeynikov.p14paginglibrary.employee

class EmployeeRepository {

    private val employees = mutableListOf<Employee>()

    init {
        createEmployeesList(1000)
    }

    fun getItems(currentPage: Int, count: Int): List<Employee> {
        return employees.subList((currentPage - 1) * count, currentPage * count)
    }

    fun createEmployeesList(count: Int) {
        employees.clear()
        for (i in 1..count) {
            employees.add(Employee(i, "Employee $i", 10000 * i))
        }
    }
}