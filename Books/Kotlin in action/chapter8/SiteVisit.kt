package ru.korobeynikov.chapter8

data class SiteVisit(
    val path: String,
    val duration: Double,
    val os: OS,
)