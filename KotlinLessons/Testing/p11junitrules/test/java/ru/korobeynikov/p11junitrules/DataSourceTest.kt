package ru.korobeynikov.p11junitrules

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class DataSourceTest {

    @get:Rule
    val folder = TemporaryFolder()

    @Test
    fun testUsingTempFolder() {
        folder.newFile("myfile.txt")
        folder.newFolder("subfolder")
    }
}