package ru.korobeynikov.p13roomtesting

import ru.korobeynikov.p13roomtesting.employee.Employee

class EmployeeTestHelper {
    companion object {
        fun createListOfEmployee(countEmployees: Int): ArrayList<Employee> {
            val listEmployees = arrayListOf<Employee>()
            for (i in 1..countEmployees)
                listEmployees.add(Employee(i.toLong(), "Employee $i", 10000 * i))
            return listEmployees
        }
    }
}