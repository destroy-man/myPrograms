package ru.korobeynikov.p31coroutinestesting

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var myViewModel: MainViewModel

    @Before
    fun setUp() {
        myViewModel = MainViewModel()
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun test() = runTest {
        myViewModel.showAndHideDialog()
        assertFalse(myViewModel.showDialog.value)
        testScheduler.advanceTimeBy(1001)
        assertTrue(myViewModel.showDialog.value)
        testScheduler.advanceTimeBy(3000)
        assertFalse(myViewModel.showDialog.value)
    }
}