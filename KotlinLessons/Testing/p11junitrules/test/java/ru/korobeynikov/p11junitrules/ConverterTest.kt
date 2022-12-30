package ru.korobeynikov.p11junitrules

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class ConverterTest {

    @Rule
    @JvmField
    val folder = TemporaryFolder()

    @Test
    fun testUsingTempFolder() {
        val createdFile = folder.newFile("myfile.txt")
        val createdFolder = folder.newFolder("subfolder")
        println("${createdFolder.path}\n${createdFile.path}")
    }
}