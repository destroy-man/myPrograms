package ru.korobeynikov.p15paginglibraryplaceholders

data class EmployeeData(
    val data: List<Employee>,
    val position: Int,
    val count: Int = 0
)
