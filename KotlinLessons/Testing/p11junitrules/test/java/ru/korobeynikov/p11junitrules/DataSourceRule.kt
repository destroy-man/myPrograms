package ru.korobeynikov.p11junitrules

import org.junit.rules.ExternalResource

class DataSourceRule(address: String) : ExternalResource() {

    private val dataSource = DataSource(address)

    override fun before() {
        dataSource.connect()
    }

    override fun after() {
        dataSource.disconnect()
    }
}